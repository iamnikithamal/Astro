package com.astro.storm.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.astro.storm.R
import io.noties.markwon.Markwon
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.linkify.LinkifyPlugin

/**
 * Custom TextView that fixes the drag shadow crash on long-press copy.
 *
 * The crash occurs when Android attempts to create a drag shadow with
 * zero or negative dimensions (IllegalStateException: Drag shadow dimensions must be positive).
 * This can happen with empty text, invisible text, or edge cases in text selection.
 *
 * This custom view overrides startDragAndDrop to provide a safe drag shadow
 * that always has valid dimensions.
 */
private class SafeSelectableTextView(context: Context) : TextView(context) {

    override fun startDragAndDrop(
        data: android.content.ClipData?,
        shadowBuilder: DragShadowBuilder?,
        myLocalState: Any?,
        flags: Int
    ): Boolean {
        // Use a safe drag shadow builder that guarantees positive dimensions
        val safeShadowBuilder = SafeDragShadowBuilder(this)
        return try {
            super.startDragAndDrop(data, safeShadowBuilder, myLocalState, flags)
        } catch (e: IllegalStateException) {
            // If drag still fails somehow, return false gracefully instead of crashing
            false
        }
    }

    @Suppress("DEPRECATION")
    override fun startDrag(
        data: android.content.ClipData?,
        shadowBuilder: DragShadowBuilder?,
        myLocalState: Any?,
        flags: Int
    ): Boolean {
        val safeShadowBuilder = SafeDragShadowBuilder(this)
        return try {
            super.startDrag(data, safeShadowBuilder, myLocalState, flags)
        } catch (e: IllegalStateException) {
            false
        }
    }

    /**
     * A DragShadowBuilder that guarantees valid dimensions.
     * Falls back to minimum 1x1 size if the view has invalid dimensions.
     */
    private class SafeDragShadowBuilder(view: View) : DragShadowBuilder(view) {

        override fun onProvideShadowMetrics(outShadowSize: Point, outShadowTouchPoint: Point) {
            val view = view
            if (view != null) {
                val width = maxOf(view.width, 1)
                val height = maxOf(view.height, 1)
                outShadowSize.set(width, height)
                outShadowTouchPoint.set(width / 2, height / 2)
            } else {
                // Fallback to minimum valid dimensions
                outShadowSize.set(1, 1)
                outShadowTouchPoint.set(0, 0)
            }
        }

        override fun onDrawShadow(canvas: Canvas) {
            val view = view
            if (view != null && view.width > 0 && view.height > 0) {
                view.draw(canvas)
            }
            // If view is invalid, draw nothing (empty shadow is better than crash)
        }
    }
}

/**
 * Content cleaning utilities for AI responses.
 * Removes tool call blocks, artifacts, and other non-display content.
 */
object ContentCleaner {

    // Pattern to match tool_call code blocks (various formats models might use)
    private val toolCallBlockPatterns = listOf(
        // Standard tool_call code block
        Regex("""```tool_call\s*\n?\s*\{[\s\S]*?\}\s*\n?```""", RegexOption.MULTILINE),
        // JSON code block with tool call inside
        Regex("""```json\s*\n?\s*\{"tool"\s*:[\s\S]*?\}\s*\n?```""", RegexOption.MULTILINE),
        // Plain code block with tool call
        Regex("""```\s*\n?\s*\{"tool"\s*:[\s\S]*?\}\s*\n?```""", RegexOption.MULTILINE)
    )

    // Pattern to match inline tool call JSON (various formats)
    private val inlineToolCallPatterns = listOf(
        // Standard format
        Regex("""\{"tool"\s*:\s*"[^"]+"\s*,\s*"arguments"\s*:\s*\{[^}]*\}\s*\}"""),
        // Alternate order
        Regex("""\{"arguments"\s*:\s*\{[^}]*\}\s*,\s*"tool"\s*:\s*"[^"]+"\s*\}"""),
        // With extra whitespace
        Regex("""\{\s*"tool"\s*:\s*"[^"]+"\s*,\s*"arguments"\s*:\s*\{[^}]*\}\s*\}""")
    )

    // Pattern to match thinking/reasoning blocks that shouldn't be in main content
    private val thinkingBlockPatterns = listOf(
        Regex("""<think>[\s\S]*?</think>""", RegexOption.MULTILINE),
        Regex("""<thinking>[\s\S]*?</thinking>""", RegexOption.MULTILINE),
        Regex("""<reasoning>[\s\S]*?</reasoning>""", RegexOption.MULTILINE)
    )

    // Pattern to match common AI response artifacts
    private val artifactPatterns = listOf(
        // Remove standalone "null" text artifacts (common in streaming)
        Regex("""(?<![a-zA-Z0-9_])null(?![a-zA-Z0-9_])"""),
        // Remove standalone "undefined" text artifacts
        Regex("""(?<![a-zA-Z0-9_])undefined(?![a-zA-Z0-9_])"""),
        // Remove empty code blocks
        Regex("""```\s*```"""),
        // Remove lone backticks at start/end of line
        Regex("""^```\s*$""", RegexOption.MULTILINE),
        // Remove trailing backticks without opening
        Regex("""(?<![`])```$"""),
        // Remove [DONE] markers that might leak through
        Regex("""\[DONE\]"""),
        // Remove data: prefixes that might leak through
        Regex("""^data:\s*""", RegexOption.MULTILINE)
    )

    /**
     * Clean content for display by removing tool calls, artifacts, and formatting issues.
     * This should be called before displaying any AI response content.
     */
    fun cleanForDisplay(content: String): String {
        if (content.isBlank()) return ""

        var cleaned = content

        // Remove tool call blocks (all patterns)
        for (pattern in toolCallBlockPatterns) {
            cleaned = pattern.replace(cleaned, "")
        }

        // Remove inline tool call JSON (all patterns)
        for (pattern in inlineToolCallPatterns) {
            cleaned = pattern.replace(cleaned, "")
        }

        // Remove thinking blocks (these should be shown separately)
        for (pattern in thinkingBlockPatterns) {
            cleaned = pattern.replace(cleaned, "")
        }

        // Remove common artifacts
        for (pattern in artifactPatterns) {
            cleaned = pattern.replace(cleaned, "")
        }

        // Clean up excessive whitespace while preserving markdown formatting
        cleaned = cleaned
            .replace(Regex("\n{4,}"), "\n\n\n") // Max 3 newlines
            .replace(Regex("[ \t]+\n"), "\n") // Remove trailing spaces
            .replace(Regex("\n[ \t]+\n"), "\n\n") // Remove lines with only whitespace
            .trim()

        return cleaned
    }

    /**
     * Clean reasoning/thinking content.
     * Similar to cleanForDisplay but preserves internal thinking format.
     */
    fun cleanReasoning(content: String): String {
        if (content.isBlank()) return ""

        var cleaned = content

        // Remove null/undefined artifacts
        cleaned = cleaned
            .replace(Regex("""(?<![a-zA-Z])null(?![a-zA-Z])"""), "")
            .replace(Regex("""(?<![a-zA-Z])undefined(?![a-zA-Z])"""), "")

        // Clean up whitespace
        cleaned = cleaned
            .replace(Regex("\n{4,}"), "\n\n\n")
            .trim()

        return cleaned
    }

    /**
     * Check if content appears to still be streaming (incomplete).
     * Useful for determining if we should show a typing indicator.
     */
    fun isIncompleteContent(content: String): Boolean {
        if (content.isBlank()) return true

        val trimmed = content.trim()

        // Check for incomplete code blocks
        val codeBlockCount = trimmed.split("```").size - 1
        if (codeBlockCount % 2 != 0) return true

        // Check for incomplete JSON
        val openBraces = trimmed.count { it == '{' }
        val closeBraces = trimmed.count { it == '}' }
        if (openBraces > closeBraces) return true

        return false
    }
}

/**
 * Singleton for creating and caching Markwon instances.
 */
object MarkwonProvider {

    @Volatile
    private var instance: Markwon? = null

    fun getInstance(context: Context): Markwon {
        return instance ?: synchronized(this) {
            instance ?: createMarkwon(context).also { instance = it }
        }
    }

    private fun createMarkwon(context: Context): Markwon {
        return Markwon.builder(context)
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TablePlugin.create(context))
            .usePlugin(LinkifyPlugin.create())
            .build()
    }

    /**
     * Clear the cached instance (useful for theme changes)
     */
    fun clearCache() {
        instance = null
    }
}

/**
 * Composable for rendering Markdown text using Markwon.
 *
 * @param markdown The markdown text to render
 * @param modifier Modifier for the text view
 * @param textColor Color for the text
 * @param linkColor Color for links
 * @param textSize Text size in sp
 * @param cleanContent Whether to clean the content before rendering (recommended)
 */
@Composable
fun MarkdownText(
    markdown: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unspecified,
    linkColor: Color = Color.Unspecified,
    textSize: Float = 14f,
    cleanContent: Boolean = true
) {
    val context = LocalContext.current
    val markwon = remember(context) { MarkwonProvider.getInstance(context) }

    // Clean content if requested
    val displayContent = remember(markdown, cleanContent) {
        if (cleanContent) ContentCleaner.cleanForDisplay(markdown) else markdown
    }

    // Pre-parse the markdown for better performance
    val spanned = remember(displayContent) {
        markwon.toMarkdown(displayContent)
    }

    // Load Poppins font for consistent typography
    val poppinsTypeface = remember(context) {
        try {
            ResourcesCompat.getFont(context, R.font.poppins_regular)
        } catch (e: Exception) {
            null
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            SafeSelectableTextView(ctx).apply {
                movementMethod = LinkMovementMethod.getInstance()
                setTextIsSelectable(true)
                // Apply Poppins font
                poppinsTypeface?.let { typeface = it }
                // Set line spacing for better readability
                setLineSpacing(4f, 1.1f)
            }
        },
        update = { textView ->
            // Apply text color if specified
            if (textColor != Color.Unspecified) {
                textView.setTextColor(textColor.toArgb())
            }

            // Apply link color if specified
            if (linkColor != Color.Unspecified) {
                textView.setLinkTextColor(linkColor.toArgb())
            }

            // Apply text size
            textView.textSize = textSize

            // Ensure font is applied on update too
            poppinsTypeface?.let { textView.typeface = it }

            // Set the markdown content
            markwon.setParsedMarkdown(textView, spanned)
        }
    )
}

/**
 * Simplified MarkdownText that uses theme colors automatically.
 */
@Composable
fun ThemedMarkdownText(
    markdown: String,
    modifier: Modifier = Modifier,
    textColor: Color,
    linkColor: Color,
    textSize: Float = 14f
) {
    MarkdownText(
        markdown = markdown,
        modifier = modifier,
        textColor = textColor,
        linkColor = linkColor,
        textSize = textSize,
        cleanContent = true
    )
}

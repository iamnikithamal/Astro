package com.astro.storm.ui.components

import android.content.Context
import android.text.method.LinkMovementMethod
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
 * Custom TextView that handles drag operations safely.
 *
 * The crash occurs when Android attempts to create a drag shadow with
 * zero or negative dimensions (IllegalStateException: Drag shadow dimensions must be positive).
 * This can happen with empty text, invisible text, or edge cases in text selection.
 *
 * This custom view disables drag-and-drop functionality to prevent crashes while
 * still allowing text selection and copy operations.
 */
private class SafeSelectableTextView(context: Context) : TextView(context) {

    init {
        // Disable drag-and-drop to prevent crashes with invalid shadow dimensions
        // Text selection and copy still work via the context menu
        setOnLongClickListener { false }
    }
}

/**
 * Content cleaning utilities for AI responses.
 *
 * This utility handles the critical task of cleaning AI-generated content
 * before display. It addresses several common issues:
 *
 * 1. Tool Call Artifacts: AI models may include tool call JSON in their
 *    responses that should not be displayed to users.
 *
 * 2. Streaming Artifacts: During streaming, various artifacts like "null",
 *    "undefined", "[DONE]" markers, and incomplete code blocks may appear.
 *
 * 3. Content Duplication: AI models (especially after tool calls) may
 *    repeat content. This cleaner detects and removes duplicated sections.
 *
 * 4. Thinking/Reasoning Tags: Content wrapped in thinking tags should be
 *    displayed separately in the reasoning panel.
 *
 * 5. Whitespace Normalization: Excessive whitespace is normalized while
 *    preserving intentional markdown formatting.
 */
object ContentCleaner {

    // Pattern to match tool_call code blocks (various formats models might use)
    private val toolCallBlockPatterns = listOf(
        // Standard tool_call code block
        Regex("""```tool_call\s*\n?\s*\{[\s\S]*?\}\s*\n?```""", RegexOption.MULTILINE),
        // JSON code block with tool call inside
        Regex("""```json\s*\n?\s*\{"tool"\s*:[\s\S]*?\}\s*\n?```""", RegexOption.MULTILINE),
        // Plain code block with tool call
        Regex("""```\s*\n?\s*\{"tool"\s*:[\s\S]*?\}\s*\n?```""", RegexOption.MULTILINE),
        // Code block with function name
        Regex("""```\s*\n?\s*\{"name"\s*:[\s\S]*?\}\s*\n?```""", RegexOption.MULTILINE),
        // Code block with nested arguments (multi-line)
        Regex("""```(?:json|tool_call)?\s*\n\s*\{[^`]*"tool"[^`]*\}\s*\n```""", RegexOption.MULTILINE)
    )

    // Pattern to match inline tool call JSON (various formats)
    private val inlineToolCallPatterns = listOf(
        // Standard format
        Regex("""\{"tool"\s*:\s*"[^"]+"\s*,\s*"arguments"\s*:\s*\{[^}]*\}\s*\}"""),
        // Alternate order
        Regex("""\{"arguments"\s*:\s*\{[^}]*\}\s*,\s*"tool"\s*:\s*"[^"]+"\s*\}"""),
        // With extra whitespace
        Regex("""\{\s*"tool"\s*:\s*"[^"]+"\s*,\s*"arguments"\s*:\s*\{[^}]*\}\s*\}"""),
        // Name-first format
        Regex("""\{\s*"name"\s*:\s*"[^"]+"\s*,\s*"parameters"\s*:\s*\{[^}]*\}\s*\}"""),
        // Function call format
        Regex("""\{\s*"function"\s*:\s*"[^"]+"\s*,\s*"args"\s*:\s*\{[^}]*\}\s*\}""")
    )

    // Pattern to match thinking/reasoning blocks that shouldn't be in main content
    private val thinkingBlockPatterns = listOf(
        Regex("""<think>[\s\S]*?</think>""", RegexOption.MULTILINE),
        Regex("""<thinking>[\s\S]*?</thinking>""", RegexOption.MULTILINE),
        Regex("""<reasoning>[\s\S]*?</reasoning>""", RegexOption.MULTILINE),
        Regex("""<reflection>[\s\S]*?</reflection>""", RegexOption.MULTILINE)
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
        Regex("""^data:\s*""", RegexOption.MULTILINE),
        // Remove SSE event markers
        Regex("""^event:\s*\w+\s*$""", RegexOption.MULTILINE),
        // Remove id: markers from SSE
        Regex("""^id:\s*\S+\s*$""", RegexOption.MULTILINE)
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

        // Detect and remove content duplication (common after tool calls)
        cleaned = removeDuplicatedContent(cleaned)

        // Clean up excessive whitespace while preserving markdown formatting
        cleaned = cleaned
            .replace(Regex("\n{4,}"), "\n\n\n") // Max 3 newlines
            .replace(Regex("[ \t]+\n"), "\n") // Remove trailing spaces
            .replace(Regex("\n[ \t]+\n"), "\n\n") // Remove lines with only whitespace
            .trim()

        return cleaned
    }

    /**
     * Detect and remove duplicated content sections.
     *
     * AI models sometimes repeat content after tool calls. This function
     * detects when a significant portion of the content is duplicated
     * and removes the repetition.
     *
     * Detection algorithm:
     * 1. Split content into paragraphs/sections
     * 2. Look for repeating sequences of paragraphs
     * 3. If found, keep only the first occurrence
     */
    private fun removeDuplicatedContent(content: String): String {
        if (content.length < 200) return content // Too short to have meaningful duplication

        // Split into paragraphs (using double newlines as delimiter)
        val paragraphs = content.split(Regex("\n\n+"))
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        if (paragraphs.size < 4) return content // Not enough paragraphs to detect duplication

        // Look for repeating sequences
        for (windowSize in (paragraphs.size / 2) downTo 2) {
            // Check if the first windowSize paragraphs repeat
            val firstHalf = paragraphs.take(windowSize)
            val secondHalf = paragraphs.drop(windowSize).take(windowSize)

            // Calculate similarity between the two halves
            if (firstHalf.size == secondHalf.size) {
                val matchCount = firstHalf.zip(secondHalf).count { (a, b) ->
                    a == b || calculateSimilarity(a, b) > 0.9
                }

                // If more than 80% of paragraphs match, we have duplication
                if (matchCount.toFloat() / firstHalf.size > 0.8) {
                    // Return only the first occurrence plus any remaining unique content
                    val uniqueContent = paragraphs.take(windowSize) +
                        paragraphs.drop(windowSize * 2)
                    return uniqueContent.joinToString("\n\n")
                }
            }
        }

        // Also check for exact substring duplication (simpler case)
        val halfLength = content.length / 2
        if (halfLength > 100) {
            val firstHalf = content.substring(0, halfLength).trim()
            val secondHalf = content.substring(halfLength).trim()

            // Check if second half starts with the beginning of first half
            if (secondHalf.startsWith(firstHalf.take(100)) ||
                calculateSimilarity(firstHalf, secondHalf) > 0.85) {
                return firstHalf
            }
        }

        return content
    }

    /**
     * Calculate similarity ratio between two strings (0.0 to 1.0)
     */
    private fun calculateSimilarity(a: String, b: String): Float {
        if (a == b) return 1.0f
        if (a.isEmpty() || b.isEmpty()) return 0.0f

        val maxLen = maxOf(a.length, b.length)
        val minLen = minOf(a.length, b.length)

        // Quick length check - if very different lengths, probably not similar
        if (minLen.toFloat() / maxLen < 0.5f) return 0.0f

        // Compare word sets for similarity
        val wordsA = a.lowercase().split(Regex("\\s+")).toSet()
        val wordsB = b.lowercase().split(Regex("\\s+")).toSet()

        val intersection = wordsA.intersect(wordsB).size
        val union = wordsA.union(wordsB).size

        return if (union == 0) 0.0f else intersection.toFloat() / union
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

        // Remove SSE artifacts
        cleaned = cleaned
            .replace(Regex("""^data:\s*""", RegexOption.MULTILINE), "")
            .replace(Regex("""^event:\s*\w+\s*$""", RegexOption.MULTILINE), "")

        // Remove thinking tags if present (keep content)
        cleaned = cleaned
            .replace(Regex("""</?think(?:ing)?>"""), "")
            .replace(Regex("""</?reasoning>"""), "")
            .replace(Regex("""</?reflection>"""), "")

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

        // Check for incomplete thinking tags
        val hasOpenThink = trimmed.contains("<think") && !trimmed.contains("</think>")
        if (hasOpenThink) return true

        return false
    }

    /**
     * Extract thinking/reasoning content from a response
     */
    fun extractThinkingContent(content: String): String? {
        for (pattern in thinkingBlockPatterns) {
            val match = pattern.find(content)
            if (match != null) {
                // Extract content between tags
                val inner = match.value
                    .replace(Regex("""<[^>]+>"""), "")
                    .trim()
                if (inner.isNotEmpty()) return inner
            }
        }
        return null
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

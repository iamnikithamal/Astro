package com.astro.storm.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.unit.Density
import com.astro.storm.data.localization.LocalizationManager
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.AshtakavargaCalculator
import com.astro.storm.ephemeris.AspectCalculator
import com.astro.storm.ephemeris.DivisionalChartCalculator
import com.astro.storm.ephemeris.ShadbalaCalculator
import com.astro.storm.ephemeris.YogaCalculator
import com.astro.storm.ui.chart.ChartRenderer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Comprehensive Chart Export System
 *
 * Provides multiple export formats:
 * 1. PDF Report - Complete professional report with charts and analysis
 * 2. JSON Export - Full data in structured JSON format
 * 3. CSV Export - Tabular data for research purposes
 * 4. Printable Chart Sheets - A4/Letter format ready to print
 * 5. Plain Text Report - Interpretive reports
 * 6. Share-ready Images - With optional watermark
 *
 * @author AstroStorm - Ultra-Precision Vedic Astrology
 */
class ChartExporter(private val context: Context) {

    private val chartRenderer = ChartRenderer()
    private val locManager = LocalizationManager.getInstance(context)

    companion object {
        private const val PDF_PAGE_WIDTH = 595 // A4 width in points (72 dpi)
        private const val PDF_PAGE_HEIGHT = 842 // A4 height in points
        private const val PDF_MARGIN = 36
        private const val PDF_MARGIN_TOP = 48
        private const val PDF_MARGIN_BOTTOM = 36
        private const val CHART_SIZE = 320
        private const val NAVAMSA_SIZE = 200

        private const val WATERMARK_TEXT = "AstroStorm"
        private const val WATERMARK_ALPHA = 80

        private val dateFormatter = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US)
        private val displayDateFormatter = SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a", Locale.US)

        // Professional color palette (warm brown theme)
        private val COLOR_PRIMARY = Color.rgb(107, 93, 77)       // #6B5D4D - Dark brown
        private val COLOR_SECONDARY = Color.rgb(139, 115, 85)    // #8B7355 - Medium brown
        private val COLOR_ACCENT = Color.rgb(212, 175, 55)       // #D4AF37 - Gold
        private val COLOR_BACKGROUND = Color.rgb(250, 248, 245)  // #FAF8F5 - Cream
        private val COLOR_CARD_BG = Color.rgb(255, 255, 255)     // White
        private val COLOR_BORDER = Color.rgb(212, 200, 184)      // #D4C8B8 - Light brown
        private val COLOR_TEXT = Color.rgb(44, 36, 24)           // #2C2418 - Dark text
        private val COLOR_TEXT_MUTED = Color.rgb(122, 109, 93)   // #7A6D5D - Muted text
        private val COLOR_SUCCESS = Color.rgb(46, 125, 50)       // Green
        private val COLOR_WARNING = Color.rgb(237, 108, 2)       // Orange
        private val COLOR_ERROR = Color.rgb(211, 47, 47)         // Red
    }

    /**
     * Export format options
     */
    enum class ExportFormat {
        PDF,
        JSON,
        CSV,
        PNG,
        TEXT
    }

    /**
     * Export result
     */
    sealed class ExportResult {
        data class Success(val path: String, val format: ExportFormat) : ExportResult()
        data class Error(val message: String) : ExportResult()
    }

    /**
     * PDF export options
     */
    data class PdfExportOptions(
        val includeChart: Boolean = true,
        val includeNavamsa: Boolean = true,
        val includePlanetaryPositions: Boolean = true,
        val includeAspects: Boolean = true,
        val includeShadbala: Boolean = true,
        val includeYogas: Boolean = true,
        val includeAshtakavarga: Boolean = true,
        val includeDashas: Boolean = false,
        val pageSize: PageSize = PageSize.A4
    )

    enum class PageSize(val width: Int, val height: Int) {
        A4(595, 842),
        LETTER(612, 792)
    }

    /**
     * Image export options
     */
    data class ImageExportOptions(
        val width: Int = 2048,
        val height: Int = 2048,
        val addWatermark: Boolean = false,
        val watermarkText: String = WATERMARK_TEXT,
        val includeTitle: Boolean = true,
        val includeLegend: Boolean = true
    )

    // ==================== PDF EXPORT ====================

    /**
     * Generate comprehensive PDF report
     */
    suspend fun exportToPdf(
        chart: VedicChart,
        options: PdfExportOptions = PdfExportOptions(),
        density: Density
    ): ExportResult = withContext(Dispatchers.IO) {
        try {
            val document = PdfDocument()
            var pageNumber = 1

            // Page 1: Chart and Basic Info
            pageNumber = addChartPage(document, chart, options, density, pageNumber)

            // Page 2: Planetary Positions
            if (options.includePlanetaryPositions) {
                pageNumber = addPlanetaryPositionsPage(document, chart, options, pageNumber)
            }

            // Page 3: Aspects and Yogas
            if (options.includeAspects || options.includeYogas) {
                pageNumber = addAspectsYogasPage(document, chart, options, pageNumber)
            }

            // Page 4: Shadbala
            if (options.includeShadbala) {
                pageNumber = addShadbalaPage(document, chart, options, pageNumber)
            }

            // Page 5: Ashtakavarga
            if (options.includeAshtakavarga) {
                pageNumber = addAshtakavargaPage(document, chart, options, pageNumber)
            }

            // Save the document
            val fileName = "AstroStorm_${chart.birthData.name.replace(" ", "_")}_${dateFormatter.format(Date())}.pdf"
            val path = saveDocument(document, fileName)

            document.close()

            ExportResult.Success(path, ExportFormat.PDF)
        } catch (e: Exception) {
            ExportResult.Error("Failed to export PDF: ${e.message}")
        }
    }

    private fun addChartPage(
        document: PdfDocument,
        chart: VedicChart,
        options: PdfExportOptions,
        density: Density,
        pageNumber: Int
    ): Int {
        val pageInfo = PdfDocument.PageInfo.Builder(options.pageSize.width, options.pageSize.height, pageNumber).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val pageWidth = options.pageSize.width.toFloat()
        val contentWidth = pageWidth - (PDF_MARGIN * 2)

        // Draw background
        val bgPaint = Paint().apply {
            color = COLOR_BACKGROUND
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, pageWidth, options.pageSize.height.toFloat(), bgPaint)

        val paint = Paint().apply {
            isAntiAlias = true
            isSubpixelText = true
        }

        var yPos = PDF_MARGIN_TOP.toFloat()

        // Header with decorative line
        drawPageHeader(canvas, paint, pageWidth, yPos, locManager.getString(StringKeyAnalysis.EXPORT_VEDIC_REPORT))
        yPos += 40f

        // Birth Information Card
        yPos = drawBirthInfoCard(canvas, paint, chart, yPos, contentWidth)
        yPos += 24f

        // Charts side by side if both are included
        if (options.includeChart && options.includeNavamsa) {
            val chartBitmap = chartRenderer.createChartBitmap(chart, CHART_SIZE, CHART_SIZE, density)
            val navamsaData = DivisionalChartCalculator.calculateNavamsa(chart)
            val navamsaBitmap = chartRenderer.createDivisionalChartBitmap(
                navamsaData.planetPositions,
                navamsaData.ascendantLongitude,
                locManager.getString(StringKeyAnalysis.CHART_NAVAMSA),
                NAVAMSA_SIZE, NAVAMSA_SIZE, density
            )

            // Position charts side by side
            val totalChartsWidth = CHART_SIZE + NAVAMSA_SIZE + 24
            val startX = (pageWidth - totalChartsWidth) / 2f

            // Draw chart labels
            paint.textSize = 11f
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            paint.color = COLOR_PRIMARY
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText(locManager.getString(StringKeyAnalysis.CHART_RASHI), startX + CHART_SIZE / 2f, yPos, paint)
            canvas.drawText(locManager.getString(StringKeyAnalysis.CHART_NAVAMSA), startX + CHART_SIZE + 24 + NAVAMSA_SIZE / 2f, yPos, paint)
            paint.textAlign = Paint.Align.LEFT
            yPos += 12f

            // Draw charts
            canvas.drawBitmap(chartBitmap, startX, yPos, null)
            val navamsaY = yPos + (CHART_SIZE - NAVAMSA_SIZE) / 2f
            canvas.drawBitmap(navamsaBitmap, startX + CHART_SIZE + 24, navamsaY, null)

            yPos += CHART_SIZE + 20f
        } else if (options.includeChart) {
            val chartBitmap = chartRenderer.createChartBitmap(chart, CHART_SIZE, CHART_SIZE, density)
            val chartX = (pageWidth - CHART_SIZE) / 2f

            paint.textSize = 11f
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            paint.color = COLOR_PRIMARY
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText(locManager.getString(StringKeyAnalysis.CHART_RASHI), pageWidth / 2f, yPos, paint)
            paint.textAlign = Paint.Align.LEFT
            yPos += 12f

            canvas.drawBitmap(chartBitmap, chartX, yPos, null)
            yPos += CHART_SIZE + 20f
        }

        // Quick Summary Card
        yPos = drawQuickSummaryCard(canvas, paint, chart, yPos, contentWidth)

        // Footer
        addPageFooter(canvas, options.pageSize, pageNumber, paint)

        document.finishPage(page)
        return pageNumber + 1
    }

    private fun drawPageHeader(canvas: Canvas, paint: Paint, pageWidth: Float, yPos: Float, title: String) {
        // Title
        paint.textSize = 22f
        paint.typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        paint.color = COLOR_PRIMARY
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(title, pageWidth / 2f, yPos + 18f, paint)
        paint.textAlign = Paint.Align.LEFT

        // Decorative line under title
        val linePaint = Paint().apply {
            color = COLOR_ACCENT
            strokeWidth = 2f
            style = Paint.Style.STROKE
        }
        val lineWidth = 120f
        val lineX = (pageWidth - lineWidth) / 2f
        canvas.drawLine(lineX, yPos + 28f, lineX + lineWidth, yPos + 28f, linePaint)
    }

    private fun drawBirthInfoCard(canvas: Canvas, paint: Paint, chart: VedicChart, startY: Float, contentWidth: Float): Float {
        var yPos = startY
        val cardLeft = PDF_MARGIN.toFloat()
        val cardRight = cardLeft + contentWidth
        val cardPadding = 12f

        // Card background
        val cardPaint = Paint().apply {
            color = COLOR_CARD_BG
            style = Paint.Style.FILL
        }
        val borderPaint = Paint().apply {
            color = COLOR_BORDER
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }

        val cardHeight = 76f
        canvas.drawRect(cardLeft, yPos, cardRight, yPos + cardHeight, cardPaint)
        canvas.drawRect(cardLeft, yPos, cardRight, yPos + cardHeight, borderPaint)

        // Accent bar on left
        val accentPaint = Paint().apply {
            color = COLOR_ACCENT
            style = Paint.Style.FILL
        }
        canvas.drawRect(cardLeft, yPos, cardLeft + 4f, yPos + cardHeight, accentPaint)

        yPos += cardPadding

        // Name (larger, bold)
        paint.textSize = 14f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_TEXT
        canvas.drawText(chart.birthData.name, cardLeft + cardPadding + 8f, yPos + 12f, paint)
        yPos += 20f

        // Birth details in two columns
        paint.textSize = 10f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        paint.color = COLOR_TEXT_MUTED

        val col1X = cardLeft + cardPadding + 8f
        val col2X = cardLeft + contentWidth / 2f

        // Column 1
        canvas.drawText("${chart.birthData.dateTime.toLocalDate()} | ${chart.birthData.dateTime.toLocalTime()}", col1X, yPos + 10f, paint)
        yPos += 14f
        canvas.drawText(chart.birthData.location, col1X, yPos + 10f, paint)

        // Column 2 (same row as column 1 items)
        val col2Y = startY + cardPadding + 20f
        canvas.drawText("${formatCoordinate(chart.birthData.latitude.toDouble(), true)}, ${formatCoordinate(chart.birthData.longitude.toDouble(), false)}", col2X, col2Y + 10f, paint)
        canvas.drawText("TZ: ${chart.birthData.timezone}", col2X, col2Y + 24f, paint)

        return startY + cardHeight
    }

    private fun drawQuickSummaryCard(canvas: Canvas, paint: Paint, chart: VedicChart, startY: Float, contentWidth: Float): Float {
        var yPos = startY
        val cardLeft = PDF_MARGIN.toFloat()
        val cardRight = cardLeft + contentWidth

        // Section title
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_PRIMARY
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_CHART_SUMMARY), cardLeft, yPos + 12f, paint)
        yPos += 24f

        // Card background
        val cardPaint = Paint().apply {
            color = COLOR_CARD_BG
            style = Paint.Style.FILL
        }
        val borderPaint = Paint().apply {
            color = COLOR_BORDER
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }

        val cardHeight = 70f
        canvas.drawRect(cardLeft, yPos, cardRight, yPos + cardHeight, cardPaint)
        canvas.drawRect(cardLeft, yPos, cardRight, yPos + cardHeight, borderPaint)

        // Summary items in row
        paint.textSize = 9f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        paint.color = COLOR_TEXT_MUTED

        val itemWidth = contentWidth / 4f
        val itemY = yPos + 12f
        val valueY = itemY + 20f

        val ascSign = ZodiacSign.fromLongitude(chart.ascendant)
        val moonPos = chart.planetPositions.find { it.planet == Planet.MOON }
        val sunPos = chart.planetPositions.find { it.planet == Planet.SUN }

        // Item 1: Ascendant
        val item1X = cardLeft + itemWidth * 0.5f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(locManager.getString(StringKeyAnalysis.CHART_ASCENDANT_LAGNA), item1X, itemY, paint)
        paint.textSize = 12f
        paint.color = COLOR_PRIMARY
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        canvas.drawText(ascSign.displayName, item1X, valueY, paint)

        // Item 2: Moon Sign
        val item2X = cardLeft + itemWidth * 1.5f
        paint.textSize = 9f
        paint.color = COLOR_TEXT_MUTED
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_MOON_SIGN), item2X, itemY, paint)
        paint.textSize = 12f
        paint.color = COLOR_PRIMARY
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        canvas.drawText(moonPos?.sign?.displayName ?: "-", item2X, valueY, paint)

        // Item 3: Sun Sign
        val item3X = cardLeft + itemWidth * 2.5f
        paint.textSize = 9f
        paint.color = COLOR_TEXT_MUTED
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_SUN_SIGN), item3X, itemY, paint)
        paint.textSize = 12f
        paint.color = COLOR_PRIMARY
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        canvas.drawText(sunPos?.sign?.displayName ?: "-", item3X, valueY, paint)

        // Item 4: Nakshatra
        val item4X = cardLeft + itemWidth * 3.5f
        paint.textSize = 9f
        paint.color = COLOR_TEXT_MUTED
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_BIRTH_NAKSHATRA).take(10), item4X, itemY, paint)
        paint.textSize = 12f
        paint.color = COLOR_PRIMARY
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        val nakshatra = moonPos?.nakshatra?.displayName?.take(8) ?: "-"
        canvas.drawText(nakshatra, item4X, valueY, paint)

        paint.textAlign = Paint.Align.LEFT

        // Second row with more details
        val row2Y = valueY + 22f
        paint.textSize = 9f
        paint.color = COLOR_TEXT_MUTED
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        paint.textAlign = Paint.Align.CENTER

        canvas.drawText("${formatDegree(chart.ascendant)}", item1X, row2Y, paint)
        canvas.drawText(moonPos?.let { "Pada ${it.nakshatraPada}" } ?: "", item2X, row2Y, paint)
        canvas.drawText(sunPos?.let { formatDegree(it.longitude) } ?: "", item3X, row2Y, paint)
        canvas.drawText(moonPos?.let { "H${it.house}" } ?: "", item4X, row2Y, paint)

        paint.textAlign = Paint.Align.LEFT

        return yPos + cardHeight
    }

    private fun addPlanetaryPositionsPage(
        document: PdfDocument,
        chart: VedicChart,
        options: PdfExportOptions,
        pageNumber: Int
    ): Int {
        val pageInfo = PdfDocument.PageInfo.Builder(options.pageSize.width, options.pageSize.height, pageNumber).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val pageWidth = options.pageSize.width.toFloat()
        val contentWidth = pageWidth - (PDF_MARGIN * 2)

        // Draw background
        val bgPaint = Paint().apply {
            color = COLOR_BACKGROUND
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, pageWidth, options.pageSize.height.toFloat(), bgPaint)

        val paint = Paint().apply {
            isAntiAlias = true
            isSubpixelText = true
        }

        var yPos = PDF_MARGIN_TOP.toFloat()

        // Title with decorative line
        drawPageHeader(canvas, paint, pageWidth, yPos, locManager.getString(StringKeyAnalysis.EXPORT_PLANETARY_POSITIONS))
        yPos += 48f

        // Table Header
        paint.textSize = 9f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_CARD_BG

        val columns = listOf(
            locManager.getString(StringKeyAnalysis.EXPORT_PLANET),
            locManager.getString(StringKeyAnalysis.EXPORT_SIGN),
            locManager.getString(StringKeyAnalysis.EXPORT_DEGREE),
            locManager.getString(StringKeyAnalysis.EXPORT_NAKSHATRA),
            locManager.getString(StringKeyAnalysis.EXPORT_PADA),
            locManager.getString(StringKeyAnalysis.EXPORT_HOUSE),
            locManager.getString(StringKeyAnalysis.EXPORT_STATUS)
        )
        val columnWidths = listOf(55f, 75f, 65f, 95f, 35f, 45f, 85f)
        var xPos = PDF_MARGIN.toFloat()

        // Draw header background with rounded appearance
        val headerPaint = Paint().apply {
            color = COLOR_PRIMARY
            style = Paint.Style.FILL
        }
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + 22f, headerPaint)

        columns.forEachIndexed { index, column ->
            canvas.drawText(column, xPos + 4f, yPos + 15f, paint)
            xPos += columnWidths[index]
        }
        yPos += 26f

        // Table Data
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        paint.textSize = 9f

        chart.planetPositions.forEachIndexed { rowIndex, position ->
            xPos = PDF_MARGIN.toFloat()

            // Alternating row background
            if (rowIndex % 2 == 0) {
                val rowBgPaint = Paint().apply {
                    color = COLOR_CARD_BG
                    style = Paint.Style.FILL
                }
                canvas.drawRect(PDF_MARGIN.toFloat(), yPos - 2f, (pageWidth - PDF_MARGIN), yPos + 18f, rowBgPaint)
            }

            val degreeInSign = position.longitude % 30.0
            val deg = degreeInSign.toInt()
            val min = ((degreeInSign - deg) * 60).toInt()
            val sec = ((((degreeInSign - deg) * 60) - min) * 60).toInt()

            val isExaltedPlanet = isExalted(position.planet, position.sign)
            val isDebilitatedPlanet = isDebilitated(position.planet, position.sign)

            val status = buildString {
                if (position.isRetrograde) append("R ")
                if (isExaltedPlanet) append("${locManager.getString(StringKeyMatch.PLANETARY_STATUS_EXALTED)} ")
                if (isDebilitatedPlanet) append("${locManager.getString(StringKeyMatch.PLANETARY_STATUS_DEBILITATED)} ")
            }.trim().ifEmpty { "-" }

            val data = listOf(
                position.planet.displayName,
                position.sign.displayName,
                "$deg° $min' $sec\"",
                position.nakshatra.displayName,
                position.nakshatraPada.toString(),
                position.house.toString(),
                status
            )

            data.forEachIndexed { index, value ->
                // Special colors for planet name and status
                paint.color = when {
                    index == 0 -> COLOR_PRIMARY // Planet name
                    index == 6 && isExaltedPlanet -> COLOR_SUCCESS
                    index == 6 && isDebilitatedPlanet -> COLOR_ERROR
                    index == 6 && position.isRetrograde -> COLOR_WARNING
                    else -> COLOR_TEXT
                }
                paint.typeface = if (index == 0) {
                    Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                } else {
                    Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                }
                canvas.drawText(value, xPos + 4f, yPos + 12f, paint)
                xPos += columnWidths[index]
            }

            // Draw row separator
            val linePaint = Paint().apply {
                color = COLOR_BORDER
                strokeWidth = 0.5f
            }
            canvas.drawLine(PDF_MARGIN.toFloat(), yPos + 18f, (pageWidth - PDF_MARGIN), yPos + 18f, linePaint)

            yPos += 20f
        }

        // Astronomical Data Section
        yPos += 24f
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_PRIMARY
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_ASTRONOMICAL_DATA), PDF_MARGIN.toFloat(), yPos + 12f, paint)
        yPos += 24f

        // Card background for astro data
        val astroCardHeight = 100f
        val cardPaint = Paint().apply {
            color = COLOR_CARD_BG
            style = Paint.Style.FILL
        }
        val borderPaint = Paint().apply {
            color = COLOR_BORDER
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + astroCardHeight, cardPaint)
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + astroCardHeight, borderPaint)

        paint.textSize = 9f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        paint.color = COLOR_TEXT

        val astroDataY = yPos + 14f
        val col1 = PDF_MARGIN.toFloat() + 8f
        val col2 = pageWidth / 2f + 8f

        // Column 1
        canvas.drawText("${locManager.getString(StringKeyAnalysis.CHART_JULIAN_DAY)}: ${String.format("%.6f", chart.julianDay)}", col1, astroDataY, paint)
        canvas.drawText("${locManager.getString(StringKeyAnalysis.CHART_AYANAMSA)}: ${chart.ayanamsaName} (${formatDegree(chart.ayanamsa)})", col1, astroDataY + 16f, paint)
        canvas.drawText("${locManager.getString(StringKeyAnalysis.CHART_ASCENDANT_LAGNA)}: ${formatDegree(chart.ascendant)} (${ZodiacSign.fromLongitude(chart.ascendant).displayName})", col1, astroDataY + 32f, paint)

        // Column 2
        canvas.drawText("${locManager.getString(StringKeyAnalysis.CHART_MIDHEAVEN)}: ${formatDegree(chart.midheaven)} (${ZodiacSign.fromLongitude(chart.midheaven).displayName})", col2, astroDataY, paint)
        canvas.drawText("${locManager.getString(StringKeyAnalysis.CHART_HOUSE_SYSTEM)}: ${chart.houseSystem.displayName}", col2, astroDataY + 16f, paint)

        yPos += astroCardHeight + 24f

        // House Cusps Table
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_PRIMARY
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_HOUSE_CUSPS), PDF_MARGIN.toFloat(), yPos + 12f, paint)
        yPos += 24f

        // House cusps in a 4-column grid
        val houseCellWidth = contentWidth / 4f
        paint.textSize = 9f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        paint.color = COLOR_TEXT

        chart.houseCusps.forEachIndexed { index, cusp ->
            val houseNum = index + 1
            val sign = ZodiacSign.fromLongitude(cusp)
            val row = index / 4
            val col = index % 4
            val cellX = PDF_MARGIN.toFloat() + (col * houseCellWidth) + 8f
            val cellY = yPos + (row * 18f) + 10f

            paint.color = COLOR_ACCENT
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            canvas.drawText("H$houseNum", cellX, cellY, paint)

            paint.color = COLOR_TEXT
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            canvas.drawText("${sign.abbreviation} ${formatDegree(cusp % 30.0)}", cellX + 28f, cellY, paint)
        }

        addPageFooter(canvas, options.pageSize, pageNumber, paint)
        document.finishPage(page)
        return pageNumber + 1
    }

    private fun addAspectsYogasPage(
        document: PdfDocument,
        chart: VedicChart,
        options: PdfExportOptions,
        pageNumber: Int
    ): Int {
        val pageInfo = PdfDocument.PageInfo.Builder(options.pageSize.width, options.pageSize.height, pageNumber).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val pageWidth = options.pageSize.width.toFloat()
        val contentWidth = pageWidth - (PDF_MARGIN * 2)

        // Draw background
        val bgPaint = Paint().apply {
            color = COLOR_BACKGROUND
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, pageWidth, options.pageSize.height.toFloat(), bgPaint)

        val paint = Paint().apply {
            isAntiAlias = true
            isSubpixelText = true
        }

        var yPos = PDF_MARGIN_TOP.toFloat()

        if (options.includeYogas) {
            // Title with decorative line
            drawPageHeader(canvas, paint, pageWidth, yPos, locManager.getString(StringKeyAnalysis.EXPORT_YOGA_ANALYSIS))
            yPos += 48f

            val yogaAnalysis = YogaCalculator.calculateYogas(chart)

            // Summary card
            val summaryCardHeight = 50f
            val cardPaint = Paint().apply {
                color = COLOR_CARD_BG
                style = Paint.Style.FILL
            }
            val borderPaint = Paint().apply {
                color = COLOR_BORDER
                style = Paint.Style.STROKE
                strokeWidth = 1f
            }
            canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + summaryCardHeight, cardPaint)
            canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + summaryCardHeight, borderPaint)

            // Accent bar
            val accentPaint = Paint().apply {
                color = COLOR_ACCENT
                style = Paint.Style.FILL
            }
            canvas.drawRect(PDF_MARGIN.toFloat(), yPos, PDF_MARGIN.toFloat() + 4f, yPos + summaryCardHeight, accentPaint)

            paint.textSize = 10f
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            paint.color = COLOR_TEXT

            // Summary items
            val summaryY = yPos + 20f
            canvas.drawText("${locManager.getString(StringKeyAnalysis.EXPORT_TOTAL_YOGAS)} ${yogaAnalysis.allYogas.size}", PDF_MARGIN.toFloat() + 16f, summaryY, paint)
            canvas.drawText("${locManager.getString(StringKeyAnalysis.EXPORT_OVERALL_YOGA_STRENGTH)} ${String.format("%.1f", yogaAnalysis.overallYogaStrength)}%", pageWidth / 2f, summaryY, paint)

            paint.color = COLOR_SUCCESS
            canvas.drawText("Auspicious: ${yogaAnalysis.allYogas.count { it.isAuspicious }}", PDF_MARGIN.toFloat() + 16f, summaryY + 16f, paint)
            paint.color = COLOR_WARNING
            canvas.drawText("Challenging: ${yogaAnalysis.negativeYogas.size}", pageWidth / 2f, summaryY + 16f, paint)

            yPos += summaryCardHeight + 20f

            // List top yogas
            val topYogas = yogaAnalysis.allYogas
                .filter { it.isAuspicious }
                .sortedByDescending { it.strengthPercentage }
                .take(10)

            if (topYogas.isNotEmpty()) {
                paint.textSize = 11f
                paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                paint.color = COLOR_PRIMARY
                canvas.drawText("${locManager.getString(StringKeyAnalysis.EXPORT_KEY_YOGAS)}", PDF_MARGIN.toFloat(), yPos + 12f, paint)
                yPos += 20f

                paint.textSize = 9f

                topYogas.forEach { yoga ->
                    val planets = yoga.planets.joinToString(", ") { it.displayName }

                    // Yoga name with dot indicator
                    paint.color = COLOR_SUCCESS
                    canvas.drawCircle(PDF_MARGIN.toFloat() + 6f, yPos + 6f, 3f, paint)

                    paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                    paint.color = COLOR_TEXT
                    canvas.drawText("${yoga.name}", PDF_MARGIN.toFloat() + 16f, yPos + 10f, paint)

                    paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                    paint.color = COLOR_TEXT_MUTED
                    canvas.drawText("($planets)", PDF_MARGIN.toFloat() + 16f + paint.measureText("${yoga.name} "), yPos + 10f, paint)
                    yPos += 14f

                    val effectText = if (yoga.effects.length > 90) yoga.effects.substring(0, 87) + "..." else yoga.effects
                    canvas.drawText("${yoga.strength.displayName}: $effectText", PDF_MARGIN.toFloat() + 16f, yPos + 10f, paint)
                    yPos += 18f

                    if (yPos > options.pageSize.height - 100) return@forEach
                }
            }

            // Negative Yogas if any
            if (yogaAnalysis.negativeYogas.isNotEmpty() && yPos < options.pageSize.height - 150) {
                yPos += 16f
                paint.textSize = 11f
                paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                paint.color = COLOR_ERROR
                canvas.drawText("${locManager.getString(StringKeyAnalysis.EXPORT_CHALLENGING_YOGAS)}", PDF_MARGIN.toFloat(), yPos + 12f, paint)
                yPos += 20f

                paint.textSize = 9f
                paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

                yogaAnalysis.negativeYogas.take(3).forEach { yoga ->
                    // Warning dot indicator
                    paint.color = COLOR_WARNING
                    canvas.drawCircle(PDF_MARGIN.toFloat() + 6f, yPos + 6f, 3f, paint)

                    paint.color = COLOR_TEXT
                    canvas.drawText("${yoga.name}", PDF_MARGIN.toFloat() + 16f, yPos + 10f, paint)
                    yPos += 14f

                    if (yoga.cancellationFactors.isNotEmpty()) {
                        paint.color = COLOR_SUCCESS
                        canvas.drawText("${locManager.getString(StringKeyAnalysis.EXPORT_MITIGATED_BY)} ${yoga.cancellationFactors.first()}", PDF_MARGIN.toFloat() + 16f, yPos + 10f, paint)
                        yPos += 14f
                    }
                }
            }
        }

        if (options.includeAspects && yPos < options.pageSize.height - 200) {
            yPos += 24f

            // Aspects Section
            paint.textSize = 11f
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            paint.color = COLOR_PRIMARY
            canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_PLANETARY_ASPECTS), PDF_MARGIN.toFloat(), yPos + 12f, paint)
            yPos += 24f

            val aspectMatrix = AspectCalculator.calculateAspectMatrix(chart)

            paint.textSize = 9f
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

            // Show significant aspects
            val significantAspects = aspectMatrix.aspects
                .filter { it.drishtiBala > 0.5 }
                .sortedByDescending { it.drishtiBala }
                .take(12)

            significantAspects.forEachIndexed { index, aspect ->
                // Alternating background for readability
                if (index % 2 == 0) {
                    val rowBgPaint = Paint().apply {
                        color = COLOR_CARD_BG
                        style = Paint.Style.FILL
                    }
                    canvas.drawRect(PDF_MARGIN.toFloat(), yPos - 2f, (pageWidth - PDF_MARGIN), yPos + 12f, rowBgPaint)
                }

                val applying = if (aspect.isApplying) locManager.getString(StringKeyAnalysis.TRANSIT_APPLYING) else locManager.getString(StringKeyAnalysis.TRANSIT_SEPARATING)

                // Planet names in accent color
                paint.color = COLOR_PRIMARY
                paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                canvas.drawText("${aspect.aspectingPlanet.displayName}", PDF_MARGIN.toFloat() + 4f, yPos + 10f, paint)

                paint.color = COLOR_TEXT
                paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                canvas.drawText("${aspect.aspectType.displayName}", PDF_MARGIN.toFloat() + 65f, yPos + 10f, paint)

                paint.color = COLOR_PRIMARY
                paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                canvas.drawText("${aspect.aspectedPlanet.displayName}", PDF_MARGIN.toFloat() + 145f, yPos + 10f, paint)

                paint.color = COLOR_TEXT_MUTED
                paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                canvas.drawText("(${String.format("%.1f°", aspect.exactOrb)} $applying)", PDF_MARGIN.toFloat() + 210f, yPos + 10f, paint)

                yPos += 16f

                if (yPos > options.pageSize.height - 60) return@forEachIndexed
            }
        }

        addPageFooter(canvas, options.pageSize, pageNumber, paint)
        document.finishPage(page)
        return pageNumber + 1
    }

    private fun addShadbalaPage(
        document: PdfDocument,
        chart: VedicChart,
        options: PdfExportOptions,
        pageNumber: Int
    ): Int {
        val pageInfo = PdfDocument.PageInfo.Builder(options.pageSize.width, options.pageSize.height, pageNumber).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val pageWidth = options.pageSize.width.toFloat()
        val contentWidth = pageWidth - (PDF_MARGIN * 2)

        // Draw background
        val bgPaint = Paint().apply {
            color = COLOR_BACKGROUND
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, pageWidth, options.pageSize.height.toFloat(), bgPaint)

        val paint = Paint().apply {
            isAntiAlias = true
            isSubpixelText = true
        }

        var yPos = PDF_MARGIN_TOP.toFloat()

        // Title with decorative line
        drawPageHeader(canvas, paint, pageWidth, yPos, locManager.getString(StringKeyAnalysis.EXPORT_SHADBALA_ANALYSIS))
        yPos += 48f

        val shadbala = ShadbalaCalculator.calculateShadbala(chart)

        // Summary Card
        val summaryCardHeight = 70f
        val cardPaint = Paint().apply {
            color = COLOR_CARD_BG
            style = Paint.Style.FILL
        }
        val borderPaint = Paint().apply {
            color = COLOR_BORDER
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + summaryCardHeight, cardPaint)
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + summaryCardHeight, borderPaint)

        // Accent bar
        val accentPaint = Paint().apply {
            color = COLOR_ACCENT
            style = Paint.Style.FILL
        }
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, PDF_MARGIN.toFloat() + 4f, yPos + summaryCardHeight, accentPaint)

        // Summary items in row
        paint.textSize = 9f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        paint.color = COLOR_TEXT_MUTED

        val itemWidth = contentWidth / 3f
        val itemY = yPos + 18f
        val valueY = itemY + 22f

        // Item 1: Overall Strength
        val item1X = PDF_MARGIN.toFloat() + itemWidth * 0.5f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_OVERALL_CHART_STRENGTH), item1X, itemY, paint)
        paint.textSize = 16f
        paint.color = COLOR_PRIMARY
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        canvas.drawText("${String.format("%.1f", shadbala.overallStrengthScore)}%", item1X, valueY, paint)

        // Item 2: Strongest Planet
        val item2X = PDF_MARGIN.toFloat() + itemWidth * 1.5f
        paint.textSize = 9f
        paint.color = COLOR_TEXT_MUTED
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_STRONGEST_PLANET), item2X, itemY, paint)
        paint.textSize = 14f
        paint.color = COLOR_SUCCESS
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        canvas.drawText(shadbala.strongestPlanet.displayName, item2X, valueY, paint)

        // Item 3: Weakest Planet
        val item3X = PDF_MARGIN.toFloat() + itemWidth * 2.5f
        paint.textSize = 9f
        paint.color = COLOR_TEXT_MUTED
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_WEAKEST_PLANET), item3X, itemY, paint)
        paint.textSize = 14f
        paint.color = COLOR_ERROR
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        canvas.drawText(shadbala.weakestPlanet.displayName, item3X, valueY, paint)

        paint.textAlign = Paint.Align.LEFT
        yPos += summaryCardHeight + 24f

        // Section title
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_PRIMARY
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_PLANET) + " " + locManager.getString(StringKeyAnalysis.EXPORT_STRENGTH_BREAKDOWN), PDF_MARGIN.toFloat(), yPos + 12f, paint)
        yPos += 24f

        // Detailed Table Header
        paint.textSize = 9f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_CARD_BG

        val columns = listOf(
            locManager.getString(StringKeyAnalysis.EXPORT_PLANET),
            locManager.getString(StringKeyAnalysis.EXPORT_TOTAL_RUPAS),
            locManager.getString(StringKeyAnalysis.EXPORT_REQUIRED),
            locManager.getString(StringKeyAnalysis.EXPORT_PERCENT),
            locManager.getString(StringKeyAnalysis.EXPORT_RATING)
        )
        val columnWidths = listOf(70f, 85f, 75f, 65f, 130f)
        var xPos = PDF_MARGIN.toFloat()

        // Header background
        val headerPaint = Paint().apply {
            color = COLOR_PRIMARY
            style = Paint.Style.FILL
        }
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + 22f, headerPaint)

        columns.forEachIndexed { index, column ->
            canvas.drawText(column, xPos + 4f, yPos + 15f, paint)
            xPos += columnWidths[index]
        }
        yPos += 26f

        // Data rows
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

        shadbala.getPlanetsByStrength().forEachIndexed { rowIndex, planetShadbala ->
            xPos = PDF_MARGIN.toFloat()

            // Alternating row background
            if (rowIndex % 2 == 0) {
                val rowBgPaint = Paint().apply {
                    color = COLOR_CARD_BG
                    style = Paint.Style.FILL
                }
                canvas.drawRect(PDF_MARGIN.toFloat(), yPos - 2f, (pageWidth - PDF_MARGIN), yPos + 18f, rowBgPaint)
            }

            val data = listOf(
                planetShadbala.planet.displayName,
                String.format("%.2f", planetShadbala.totalRupas),
                String.format("%.2f", planetShadbala.requiredRupas),
                String.format("%.0f%%", planetShadbala.percentageOfRequired),
                planetShadbala.strengthRating.displayName
            )

            data.forEachIndexed { index, value ->
                // Color code based on strength and column
                paint.color = when {
                    index == 0 -> COLOR_PRIMARY // Planet name
                    index == 3 && planetShadbala.percentageOfRequired >= 100 -> COLOR_SUCCESS
                    index == 3 && planetShadbala.percentageOfRequired < 80 -> COLOR_ERROR
                    index == 4 && planetShadbala.percentageOfRequired >= 100 -> COLOR_SUCCESS
                    index == 4 && planetShadbala.percentageOfRequired < 80 -> COLOR_ERROR
                    else -> COLOR_TEXT
                }
                paint.typeface = if (index == 0) {
                    Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                } else {
                    Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
                }
                canvas.drawText(value, xPos + 4f, yPos + 12f, paint)
                xPos += columnWidths[index]
            }

            // Row separator
            val linePaint = Paint().apply {
                color = COLOR_BORDER
                strokeWidth = 0.5f
            }
            canvas.drawLine(PDF_MARGIN.toFloat(), yPos + 18f, (pageWidth - PDF_MARGIN), yPos + 18f, linePaint)

            yPos += 20f
        }

        // Detailed breakdown for strongest planet
        yPos += 24f
        val strongest = shadbala.planetaryStrengths[shadbala.strongestPlanet]
        if (strongest != null) {
            paint.textSize = 11f
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            paint.color = COLOR_PRIMARY
            canvas.drawText("${strongest.planet.displayName} ${locManager.getString(StringKeyAnalysis.EXPORT_STRENGTH_BREAKDOWN)}", PDF_MARGIN.toFloat(), yPos + 12f, paint)
            yPos += 24f

            // Breakdown card
            val breakdownCardHeight = 110f
            canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + breakdownCardHeight, cardPaint)
            canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + breakdownCardHeight, borderPaint)

            paint.textSize = 9f
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

            val breakdown = listOf(
                Pair(locManager.getString(StringKeyAnalysis.DIALOG_STHANA_BALA), strongest.sthanaBala.total),
                Pair(locManager.getString(StringKeyAnalysis.DIALOG_DIG_BALA), strongest.digBala),
                Pair(locManager.getString(StringKeyAnalysis.DIALOG_KALA_BALA), strongest.kalaBala.total),
                Pair(locManager.getString(StringKeyAnalysis.DIALOG_CHESTA_BALA), strongest.chestaBala),
                Pair(locManager.getString(StringKeyAnalysis.DIALOG_NAISARGIKA_BALA), strongest.naisargikaBala),
                Pair(locManager.getString(StringKeyAnalysis.DIALOG_DRIK_BALA), strongest.drikBala)
            )

            val col1X = PDF_MARGIN.toFloat() + 12f
            val col2X = pageWidth / 2f + 12f
            var breakdownY = yPos + 16f

            breakdown.forEachIndexed { index, (label, value) ->
                val drawX = if (index % 2 == 0) col1X else col2X
                if (index % 2 == 0 && index > 0) breakdownY += 16f

                paint.color = COLOR_TEXT_MUTED
                canvas.drawText(label, drawX, breakdownY + 10f, paint)
                paint.color = COLOR_PRIMARY
                paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
                canvas.drawText("${String.format("%.1f", value)} ${locManager.getString(StringKeyAnalysis.EXPORT_VIRUPAS)}", drawX + 100f, breakdownY + 10f, paint)
                paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            }
        }

        addPageFooter(canvas, options.pageSize, pageNumber, paint)
        document.finishPage(page)
        return pageNumber + 1
    }

    private fun addAshtakavargaPage(
        document: PdfDocument,
        chart: VedicChart,
        options: PdfExportOptions,
        pageNumber: Int
    ): Int {
        val pageInfo = PdfDocument.PageInfo.Builder(options.pageSize.width, options.pageSize.height, pageNumber).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        val pageWidth = options.pageSize.width.toFloat()
        val contentWidth = pageWidth - (PDF_MARGIN * 2)

        // Draw background
        val bgPaint = Paint().apply {
            color = COLOR_BACKGROUND
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, pageWidth, options.pageSize.height.toFloat(), bgPaint)

        val paint = Paint().apply {
            isAntiAlias = true
            isSubpixelText = true
        }

        var yPos = PDF_MARGIN_TOP.toFloat()

        // Title with decorative line
        drawPageHeader(canvas, paint, pageWidth, yPos, locManager.getString(StringKeyAnalysis.EXPORT_ASHTAKAVARGA_ANALYSIS))
        yPos += 48f

        val ashtakavarga = AshtakavargaCalculator.calculateAshtakavarga(chart)

        // SAV Section Title
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_PRIMARY
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_SARVASHTAKAVARGA), PDF_MARGIN.toFloat(), yPos + 12f, paint)
        yPos += 24f

        // SAV Card
        val savCardHeight = 55f
        val cardPaint = Paint().apply {
            color = COLOR_CARD_BG
            style = Paint.Style.FILL
        }
        val borderPaint = Paint().apply {
            color = COLOR_BORDER
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + savCardHeight, cardPaint)
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + savCardHeight, borderPaint)

        // SAV Table Header - zodiac signs
        val signWidth = (contentWidth - 35f) / 12f
        var xPos = PDF_MARGIN.toFloat() + 35f

        paint.textSize = 8f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_TEXT_MUTED

        ZodiacSign.entries.forEach { sign ->
            canvas.drawText(sign.abbreviation, xPos + 2f, yPos + 16f, paint)
            xPos += signWidth
        }

        // SAV Values row
        xPos = PDF_MARGIN.toFloat() + 8f
        paint.color = COLOR_ACCENT
        canvas.drawText("SAV", xPos, yPos + 38f, paint)
        xPos = PDF_MARGIN.toFloat() + 35f

        paint.textSize = 10f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)

        ZodiacSign.entries.forEach { sign ->
            val bindus = ashtakavarga.sarvashtakavarga.getBindusForSign(sign)
            paint.color = when {
                bindus >= 30 -> COLOR_SUCCESS
                bindus >= 25 -> COLOR_TEXT
                else -> COLOR_ERROR
            }
            canvas.drawText(bindus.toString(), xPos + 4f, yPos + 38f, paint)
            xPos += signWidth
        }

        yPos += savCardHeight + 24f

        // BAV Section Title
        paint.textSize = 12f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_PRIMARY
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_BHINNASHTAKAVARGA), PDF_MARGIN.toFloat(), yPos + 12f, paint)
        yPos += 24f

        // BAV Card
        val bavRowHeight = 18f
        val bavCardHeight = bavRowHeight * ashtakavarga.bhinnashtakavarga.size + 30f
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + bavCardHeight, cardPaint)
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + bavCardHeight, borderPaint)

        // BAV Header row
        paint.textSize = 8f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_TEXT_MUTED

        xPos = PDF_MARGIN.toFloat() + 35f
        ZodiacSign.entries.forEach { sign ->
            canvas.drawText(sign.abbreviation, xPos + 2f, yPos + 14f, paint)
            xPos += signWidth
        }
        canvas.drawText("Tot", xPos + 2f, yPos + 14f, paint)

        yPos += 18f

        // BAV Data rows
        paint.textSize = 9f

        ashtakavarga.bhinnashtakavarga.forEachIndexed { rowIndex, (planet, bav) ->
            xPos = PDF_MARGIN.toFloat() + 8f

            // Alternating row background
            if (rowIndex % 2 == 0) {
                val rowBgPaint = Paint().apply {
                    color = COLOR_BACKGROUND
                    style = Paint.Style.FILL
                }
                canvas.drawRect(PDF_MARGIN.toFloat() + 1f, yPos, (pageWidth - PDF_MARGIN) - 1f, yPos + bavRowHeight, rowBgPaint)
            }

            // Planet symbol
            paint.color = COLOR_PRIMARY
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            canvas.drawText(planet.symbol, xPos, yPos + 13f, paint)
            xPos = PDF_MARGIN.toFloat() + 35f

            // Bindus for each sign
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            paint.color = COLOR_TEXT

            ZodiacSign.entries.forEach { sign ->
                val bindus = bav.getBindusForSign(sign)
                // Color code: high bindus green, low bindus red
                paint.color = when {
                    bindus >= 5 -> COLOR_SUCCESS
                    bindus <= 2 -> COLOR_ERROR
                    else -> COLOR_TEXT
                }
                canvas.drawText(bindus.toString(), xPos + 4f, yPos + 13f, paint)
                xPos += signWidth
            }

            // Total
            paint.color = COLOR_ACCENT
            paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            canvas.drawText(bav.totalBindus.toString(), xPos + 4f, yPos + 13f, paint)

            yPos += bavRowHeight
        }

        yPos += 30f

        // Transit Interpretation Guide
        paint.textSize = 11f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        paint.color = COLOR_PRIMARY
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_TRANSIT_GUIDE), PDF_MARGIN.toFloat(), yPos + 12f, paint)
        yPos += 24f

        // Guide Card
        val guideCardHeight = 130f
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + guideCardHeight, cardPaint)
        canvas.drawRect(PDF_MARGIN.toFloat(), yPos, (pageWidth - PDF_MARGIN), yPos + guideCardHeight, borderPaint)

        paint.textSize = 9f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

        val col1X = PDF_MARGIN.toFloat() + 12f
        val col2X = pageWidth / 2f + 12f

        // SAV Guide (left column)
        paint.color = COLOR_ACCENT
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        canvas.drawText("SAV ${locManager.getString(StringKeyAnalysis.EXPORT_TRANSIT_GUIDE)}", col1X, yPos + 16f, paint)
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        paint.color = COLOR_TEXT

        val savGuide = listOf(
            locManager.getString(StringKeyAnalysis.EXPORT_SAV_EXCELLENT),
            locManager.getString(StringKeyAnalysis.EXPORT_SAV_GOOD),
            locManager.getString(StringKeyAnalysis.EXPORT_SAV_AVERAGE),
            locManager.getString(StringKeyAnalysis.EXPORT_SAV_CHALLENGING)
        )

        savGuide.forEachIndexed { index, line ->
            // Add colored dot indicator
            val dotColor = when (index) {
                0 -> COLOR_SUCCESS
                1 -> COLOR_SUCCESS
                2 -> COLOR_WARNING
                else -> COLOR_ERROR
            }
            val dotPaint = Paint().apply {
                color = dotColor
                style = Paint.Style.FILL
            }
            canvas.drawCircle(col1X + 4f, yPos + 32f + (index * 14f), 3f, dotPaint)
            canvas.drawText(line, col1X + 14f, yPos + 36f + (index * 14f), paint)
        }

        // BAV Guide (right column)
        paint.color = COLOR_ACCENT
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        canvas.drawText("BAV ${locManager.getString(StringKeyAnalysis.EXPORT_TRANSIT_GUIDE)}", col2X, yPos + 16f, paint)
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        paint.color = COLOR_TEXT

        val bavGuide = listOf(
            locManager.getString(StringKeyAnalysis.EXPORT_BAV_EXCELLENT),
            locManager.getString(StringKeyAnalysis.EXPORT_BAV_GOOD),
            locManager.getString(StringKeyAnalysis.EXPORT_BAV_AVERAGE),
            locManager.getString(StringKeyAnalysis.EXPORT_BAV_CHALLENGING)
        )

        bavGuide.forEachIndexed { index, line ->
            val dotColor = when (index) {
                0 -> COLOR_SUCCESS
                1 -> COLOR_SUCCESS
                2 -> COLOR_WARNING
                else -> COLOR_ERROR
            }
            val dotPaint = Paint().apply {
                color = dotColor
                style = Paint.Style.FILL
            }
            canvas.drawCircle(col2X + 4f, yPos + 32f + (index * 14f), 3f, dotPaint)
            canvas.drawText(line, col2X + 14f, yPos + 36f + (index * 14f), paint)
        }

        addPageFooter(canvas, options.pageSize, pageNumber, paint)
        document.finishPage(page)
        return pageNumber + 1
    }

    private fun addPageFooter(canvas: Canvas, pageSize: PageSize, pageNumber: Int, paint: Paint) {
        val footerY = pageSize.height - PDF_MARGIN_BOTTOM.toFloat()

        // Draw separator line
        val linePaint = Paint().apply {
            color = COLOR_BORDER
            strokeWidth = 0.5f
            style = Paint.Style.STROKE
        }
        canvas.drawLine(PDF_MARGIN.toFloat(), footerY - 12f, (pageSize.width - PDF_MARGIN).toFloat(), footerY - 12f, linePaint)

        paint.textSize = 8f
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        paint.color = COLOR_TEXT_MUTED

        // Left: Generated by AstroStorm
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_GENERATED_BY), PDF_MARGIN.toFloat(), footerY, paint)

        // Center: App branding with gold accent
        paint.textAlign = Paint.Align.CENTER
        paint.color = COLOR_ACCENT
        paint.typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC)
        canvas.drawText("AstroStorm", pageSize.width / 2f, footerY, paint)

        // Right: Page number
        paint.textAlign = Paint.Align.RIGHT
        paint.color = COLOR_TEXT_MUTED
        paint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        canvas.drawText(locManager.getString(StringKeyAnalysis.EXPORT_PAGE, pageNumber), (pageSize.width - PDF_MARGIN).toFloat(), footerY, paint)
        paint.textAlign = Paint.Align.LEFT
    }

    private fun saveDocument(document: PdfDocument, fileName: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/AstroStorm")
            }

            val uri = context.contentResolver.insert(
                MediaStore.Files.getContentUri("external"),
                contentValues
            ) ?: throw Exception("Failed to create file")

            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                document.writeTo(outputStream)
            }

            uri.toString()
        } else {
            @Suppress("DEPRECATION")
            val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val astroStormDir = File(documentsDir, "AstroStorm")
            if (!astroStormDir.exists()) astroStormDir.mkdirs()

            val file = File(astroStormDir, fileName)
            FileOutputStream(file).use { outputStream ->
                document.writeTo(outputStream)
            }

            file.absolutePath
        }
    }

    // ==================== JSON EXPORT ====================

    /**
     * Export complete chart data to JSON
     */
    suspend fun exportToJson(chart: VedicChart): ExportResult = withContext(Dispatchers.IO) {
        try {
            val json = JSONObject()

            // Birth Data
            val birthDataJson = JSONObject().apply {
                put("name", chart.birthData.name)
                put("dateTime", chart.birthData.dateTime.toString())
                put("latitude", chart.birthData.latitude)
                put("longitude", chart.birthData.longitude)
                put("timezone", chart.birthData.timezone)
                put("location", chart.birthData.location)
            }
            json.put("birthData", birthDataJson)

            // Astronomical Data
            val astroJson = JSONObject().apply {
                put("julianDay", chart.julianDay)
                put("ayanamsa", chart.ayanamsa)
                put("ayanamsaName", chart.ayanamsaName)
                put("ascendant", chart.ascendant)
                put("ascendantSign", ZodiacSign.fromLongitude(chart.ascendant).name)
                put("midheaven", chart.midheaven)
                put("midheavenSign", ZodiacSign.fromLongitude(chart.midheaven).name)
                put("houseSystem", chart.houseSystem.name)
            }
            json.put("astronomicalData", astroJson)

            // Planetary Positions
            val planetsArray = JSONArray()
            chart.planetPositions.forEach { pos ->
                val planetJson = JSONObject().apply {
                    put("planet", pos.planet.name)
                    put("longitude", pos.longitude)
                    put("latitude", pos.latitude)
                    put("sign", pos.sign.name)
                    put("signDisplayName", pos.sign.displayName)
                    put("degreeInSign", pos.longitude % 30.0)
                    put("degree", pos.degree)
                    put("minutes", pos.minutes)
                    put("seconds", pos.seconds)
                    put("house", pos.house)
                    put("nakshatra", pos.nakshatra.name)
                    put("nakshatraDisplayName", pos.nakshatra.displayName)
                    put("nakshatraPada", pos.nakshatraPada)
                    put("isRetrograde", pos.isRetrograde)
                    put("speed", pos.speed)
                    put("distance", pos.distance)
                }
                planetsArray.put(planetJson)
            }
            json.put("planetaryPositions", planetsArray)

            // House Cusps
            val cuspsArray = JSONArray()
            chart.houseCusps.forEachIndexed { index, cusp ->
                val cuspJson = JSONObject().apply {
                    put("house", index + 1)
                    put("cusp", cusp)
                    put("sign", ZodiacSign.fromLongitude(cusp).name)
                }
                cuspsArray.put(cuspJson)
            }
            json.put("houseCusps", cuspsArray)

            // Yogas
            val yogaAnalysis = YogaCalculator.calculateYogas(chart)
            val yogasArray = JSONArray()
            yogaAnalysis.allYogas.forEach { yoga ->
                val yogaJson = JSONObject().apply {
                    put("name", yoga.name)
                    put("sanskritName", yoga.sanskritName)
                    put("category", yoga.category.name)
                    put("planets", JSONArray(yoga.planets.map { it.name }))
                    put("houses", JSONArray(yoga.houses))
                    put("description", yoga.description)
                    put("effects", yoga.effects)
                    put("strength", yoga.strength.name)
                    put("strengthPercentage", yoga.strengthPercentage)
                    put("isAuspicious", yoga.isAuspicious)
                    put("activationPeriod", yoga.activationPeriod)
                }
                yogasArray.put(yogaJson)
            }
            json.put("yogas", yogasArray)

            // Shadbala
            val shadbala = ShadbalaCalculator.calculateShadbala(chart)
            val shadbalaJson = JSONObject().apply {
                put("overallStrengthScore", shadbala.overallStrengthScore)
                put("strongestPlanet", shadbala.strongestPlanet.name)
                put("weakestPlanet", shadbala.weakestPlanet.name)

                val strengthsArray = JSONArray()
                shadbala.planetaryStrengths.forEach { (planet, strength) ->
                    val strengthJson = JSONObject().apply {
                        put("planet", planet.name)
                        put("totalRupas", strength.totalRupas)
                        put("totalVirupas", strength.totalVirupas)
                        put("requiredRupas", strength.requiredRupas)
                        put("percentageOfRequired", strength.percentageOfRequired)
                        put("strengthRating", strength.strengthRating.name)
                        put("sthanaBala", strength.sthanaBala.total)
                        put("digBala", strength.digBala)
                        put("kalaBala", strength.kalaBala.total)
                        put("chestaBala", strength.chestaBala)
                        put("naisargikaBala", strength.naisargikaBala)
                        put("drikBala", strength.drikBala)
                    }
                    strengthsArray.put(strengthJson)
                }
                put("planetaryStrengths", strengthsArray)
            }
            json.put("shadbala", shadbalaJson)

            // Ashtakavarga
            val ashtakavarga = AshtakavargaCalculator.calculateAshtakavarga(chart)
            val ashtakavargaJson = JSONObject().apply {
                val savJson = JSONObject()
                ZodiacSign.entries.forEach { sign ->
                    savJson.put(sign.name, ashtakavarga.sarvashtakavarga.getBindusForSign(sign))
                }
                put("sarvashtakavarga", savJson)
                put("savTotal", ashtakavarga.sarvashtakavarga.totalBindus)
                put("strongestSign", ashtakavarga.sarvashtakavarga.strongestSign.name)
                put("weakestSign", ashtakavarga.sarvashtakavarga.weakestSign.name)

                val bavJson = JSONObject()
                ashtakavarga.bhinnashtakavarga.forEach { (planet, bav) ->
                    val planetBavJson = JSONObject()
                    ZodiacSign.entries.forEach { sign ->
                        planetBavJson.put(sign.name, bav.getBindusForSign(sign))
                    }
                    planetBavJson.put("total", bav.totalBindus)
                    bavJson.put(planet.name, planetBavJson)
                }
                put("bhinnashtakavarga", bavJson)
            }
            json.put("ashtakavarga", ashtakavargaJson)

            // Metadata
            val metadataJson = JSONObject().apply {
                put("generatedAt", System.currentTimeMillis())
                put("generatedBy", "AstroStorm")
                put("version", "1.0")
                put("calculationEngine", "Swiss Ephemeris (JPL Mode)")
            }
            json.put("metadata", metadataJson)

            // Save to file
            val fileName = "AstroStorm_${chart.birthData.name.replace(" ", "_")}_${dateFormatter.format(Date())}.json"
            val path = saveJsonFile(json.toString(2), fileName)

            ExportResult.Success(path, ExportFormat.JSON)
        } catch (e: Exception) {
            ExportResult.Error("Failed to export JSON: ${e.message}")
        }
    }

    private fun saveJsonFile(content: String, fileName: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/json")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/AstroStorm")
            }

            val uri = context.contentResolver.insert(
                MediaStore.Files.getContentUri("external"),
                contentValues
            ) ?: throw Exception("Failed to create file")

            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(content.toByteArray())
            }

            uri.toString()
        } else {
            @Suppress("DEPRECATION")
            val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val astroStormDir = File(documentsDir, "AstroStorm")
            if (!astroStormDir.exists()) astroStormDir.mkdirs()

            val file = File(astroStormDir, fileName)
            FileWriter(file).use { writer ->
                writer.write(content)
            }

            file.absolutePath
        }
    }

    // ==================== CSV EXPORT ====================

    /**
     * Export chart data to CSV for research purposes
     */
    suspend fun exportToCsv(chart: VedicChart): ExportResult = withContext(Dispatchers.IO) {
        try {
            val csvBuilder = StringBuilder()

            // Planetary Positions CSV
            csvBuilder.appendLine("PLANETARY POSITIONS")
            csvBuilder.appendLine("Planet,Longitude,Sign,DegreeInSign,House,Nakshatra,Pada,Retrograde,Speed")

            chart.planetPositions.forEach { pos ->
                csvBuilder.appendLine(
                    "${pos.planet.displayName},${pos.longitude},${pos.sign.displayName}," +
                            "${pos.longitude % 30.0},${pos.house},${pos.nakshatra.displayName}," +
                            "${pos.nakshatraPada},${pos.isRetrograde},${pos.speed}"
                )
            }

            csvBuilder.appendLine()
            csvBuilder.appendLine("HOUSE CUSPS")
            csvBuilder.appendLine("House,Cusp,Sign")

            chart.houseCusps.forEachIndexed { index, cusp ->
                csvBuilder.appendLine("${index + 1},$cusp,${ZodiacSign.fromLongitude(cusp).displayName}")
            }

            csvBuilder.appendLine()
            csvBuilder.appendLine("SHADBALA")
            csvBuilder.appendLine("Planet,TotalRupas,RequiredRupas,Percentage,SthanaBala,DigBala,KalaBala,ChestaBala,NaisargikaBala,DrikBala")

            val shadbala = ShadbalaCalculator.calculateShadbala(chart)
            shadbala.planetaryStrengths.forEach { (planet, strength) ->
                csvBuilder.appendLine(
                    "${planet.displayName},${strength.totalRupas},${strength.requiredRupas}," +
                            "${strength.percentageOfRequired},${strength.sthanaBala.total},${strength.digBala}," +
                            "${strength.kalaBala.total},${strength.chestaBala},${strength.naisargikaBala},${strength.drikBala}"
                )
            }

            csvBuilder.appendLine()
            csvBuilder.appendLine("SARVASHTAKAVARGA")
            csvBuilder.append("Sign")
            ZodiacSign.entries.forEach { csvBuilder.append(",${it.abbreviation}") }
            csvBuilder.appendLine()

            val ashtakavarga = AshtakavargaCalculator.calculateAshtakavarga(chart)
            csvBuilder.append("SAV")
            ZodiacSign.entries.forEach { sign ->
                csvBuilder.append(",${ashtakavarga.sarvashtakavarga.getBindusForSign(sign)}")
            }
            csvBuilder.appendLine()

            csvBuilder.appendLine()
            csvBuilder.appendLine("BHINNASHTAKAVARGA")
            csvBuilder.append("Planet")
            ZodiacSign.entries.forEach { csvBuilder.append(",${it.abbreviation}") }
            csvBuilder.appendLine(",Total")

            ashtakavarga.bhinnashtakavarga.forEach { (planet, bav) ->
                csvBuilder.append(planet.symbol)
                ZodiacSign.entries.forEach { sign ->
                    csvBuilder.append(",${bav.getBindusForSign(sign)}")
                }
                csvBuilder.appendLine(",${bav.totalBindus}")
            }

            csvBuilder.appendLine()
            csvBuilder.appendLine("YOGAS")
            csvBuilder.appendLine("Name,Category,Planets,Strength,StrengthPercentage,IsAuspicious")

            val yogaAnalysis = YogaCalculator.calculateYogas(chart)
            yogaAnalysis.allYogas.forEach { yoga ->
                val planets = yoga.planets.joinToString(";") { it.displayName }
                csvBuilder.appendLine(
                    "\"${yoga.name}\",${yoga.category.displayName},\"$planets\"," +
                            "${yoga.strength.displayName},${yoga.strengthPercentage},${yoga.isAuspicious}"
                )
            }

            // Save to file
            val fileName = "AstroStorm_${chart.birthData.name.replace(" ", "_")}_${dateFormatter.format(Date())}.csv"
            val path = saveCsvFile(csvBuilder.toString(), fileName)

            ExportResult.Success(path, ExportFormat.CSV)
        } catch (e: Exception) {
            ExportResult.Error("Failed to export CSV: ${e.message}")
        }
    }

    private fun saveCsvFile(content: String, fileName: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/AstroStorm")
            }

            val uri = context.contentResolver.insert(
                MediaStore.Files.getContentUri("external"),
                contentValues
            ) ?: throw Exception("Failed to create file")

            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(content.toByteArray())
            }

            uri.toString()
        } else {
            @Suppress("DEPRECATION")
            val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val astroStormDir = File(documentsDir, "AstroStorm")
            if (!astroStormDir.exists()) astroStormDir.mkdirs()

            val file = File(astroStormDir, fileName)
            FileWriter(file).use { writer ->
                writer.write(content)
            }

            file.absolutePath
        }
    }

    // ==================== IMAGE EXPORT ====================

    /**
     * Export chart as image with optional watermark
     */
    suspend fun exportToImage(
        chart: VedicChart,
        options: ImageExportOptions = ImageExportOptions(),
        density: Density
    ): ExportResult = withContext(Dispatchers.IO) {
        try {
            var bitmap = chartRenderer.createChartBitmap(chart, options.width, options.height, density)

            if (options.addWatermark) {
                bitmap = addWatermark(bitmap, options.watermarkText)
            }

            if (options.includeTitle) {
                bitmap = addTitle(bitmap, chart)
            }

            val fileName = "AstroStorm_${chart.birthData.name.replace(" ", "_")}_${dateFormatter.format(Date())}.png"
            val path = saveImageFile(bitmap, fileName)

            ExportResult.Success(path, ExportFormat.PNG)
        } catch (e: Exception) {
            ExportResult.Error("Failed to export image: ${e.message}")
        }
    }

    private fun addWatermark(bitmap: Bitmap, watermarkText: String): Bitmap {
        val result = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(result)

        // Extract RGB components from COLOR_PRIMARY for consistent branding
        val r = (COLOR_PRIMARY shr 16) and 0xFF
        val g = (COLOR_PRIMARY shr 8) and 0xFF
        val b = COLOR_PRIMARY and 0xFF

        val paint = Paint().apply {
            color = Color.argb(WATERMARK_ALPHA, r, g, b)
            textSize = bitmap.width / 12f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            isAntiAlias = true
            isSubpixelText = true
            textAlign = Paint.Align.CENTER
            letterSpacing = 0.05f
        }

        // Diagonal watermark - more subtle positioning
        canvas.save()
        canvas.rotate(-25f, bitmap.width / 2f, bitmap.height / 2f)
        canvas.drawText(watermarkText, bitmap.width / 2f, bitmap.height / 2f, paint)
        canvas.restore()

        return result
    }

    private fun addTitle(bitmap: Bitmap, chart: VedicChart): Bitmap {
        val scale = bitmap.width / 2048f // Scale based on standard export size
        val titleHeight = (100 * scale).coerceAtLeast(60f).toInt()
        val padding = (16 * scale).coerceAtLeast(8f)

        val result = Bitmap.createBitmap(bitmap.width, bitmap.height + titleHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)

        // Draw full background
        val bgPaint = Paint().apply {
            color = COLOR_BACKGROUND
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, bitmap.width.toFloat(), (bitmap.height + titleHeight).toFloat(), bgPaint)

        // Draw title area with accent bar
        val titleBgPaint = Paint().apply {
            color = COLOR_CARD_BG
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, bitmap.width.toFloat(), titleHeight.toFloat(), titleBgPaint)

        // Gold accent bar at top
        val accentPaint = Paint().apply {
            color = COLOR_ACCENT
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, bitmap.width.toFloat(), (4 * scale).coerceAtLeast(2f), accentPaint)

        // Draw name - larger, centered, professional color
        val nameTextSize = (32 * scale).coerceAtLeast(18f)
        val textPaint = Paint().apply {
            color = COLOR_PRIMARY
            textSize = nameTextSize
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            isAntiAlias = true
            isSubpixelText = true
            textAlign = Paint.Align.CENTER
        }

        // Handle empty or blank names gracefully
        val displayName = chart.birthData.name.takeIf { it.isNotBlank() } ?: "Birth Chart"
        val nameY = titleHeight * 0.42f
        canvas.drawText(displayName, bitmap.width / 2f, nameY, textPaint)

        // Draw subtitle with date/location
        val subtitleTextSize = (18 * scale).coerceAtLeast(11f)
        textPaint.textSize = subtitleTextSize
        textPaint.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        textPaint.color = COLOR_TEXT_MUTED

        val dateStr = try {
            chart.birthData.dateTime.toLocalDate().toString()
        } catch (_: Exception) {
            ""
        }
        val timeStr = try {
            chart.birthData.dateTime.toLocalTime().toString()
        } catch (_: Exception) {
            ""
        }
        val locationStr = chart.birthData.location.takeIf { it.isNotBlank() } ?: ""

        val subtitle = listOfNotNull(
            dateStr.takeIf { it.isNotBlank() },
            timeStr.takeIf { it.isNotBlank() },
            locationStr.takeIf { it.isNotBlank() }
        ).joinToString(" | ")

        val subtitleY = titleHeight * 0.75f
        canvas.drawText(subtitle, bitmap.width / 2f, subtitleY, textPaint)

        // Draw separator line
        val linePaint = Paint().apply {
            color = COLOR_BORDER
            strokeWidth = (1 * scale).coerceAtLeast(0.5f)
            style = Paint.Style.STROKE
        }
        canvas.drawLine(padding, titleHeight.toFloat() - 1f, bitmap.width - padding, titleHeight.toFloat() - 1f, linePaint)

        // Draw chart below title
        canvas.drawBitmap(bitmap, 0f, titleHeight.toFloat(), null)

        return result
    }

    private fun saveImageFile(bitmap: Bitmap, fileName: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/AstroStorm")
            }

            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ) ?: throw Exception("Failed to create file")

            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }

            uri.toString()
        } else {
            @Suppress("DEPRECATION")
            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val astroStormDir = File(picturesDir, "AstroStorm")
            if (!astroStormDir.exists()) astroStormDir.mkdirs()

            val file = File(astroStormDir, fileName)
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }

            file.absolutePath
        }
    }

    // ==================== TEXT REPORT EXPORT ====================

    /**
     * Export interpretive text report
     */
    suspend fun exportToText(chart: VedicChart): ExportResult = withContext(Dispatchers.IO) {
        try {
            val report = buildString {
                appendLine("═══════════════════════════════════════════════════════════════════════════════")
                appendLine("                         ${locManager.getString(StringKeyAnalysis.EXPORT_VEDIC_REPORT)}")
                appendLine("                              ${locManager.getString(StringKeyAnalysis.EXPORT_GENERATED_BY_SHORT)}")
                appendLine("═══════════════════════════════════════════════════════════════════════════════")
                appendLine()
                appendLine("${locManager.getString(StringKeyAnalysis.EXPORT_GENERATED)}: ${displayDateFormatter.format(Date())}")
                appendLine()

                // Birth Information
                appendLine(locManager.getString(StringKeyAnalysis.EXPORT_BIRTH_INFO))
                appendLine("─────────────────────────────────────────────────────────────────────────────")
                appendLine("${locManager.getString(StringKeyAnalysis.EXPORT_NAME)} ${chart.birthData.name}")
                appendLine("${locManager.getString(StringKeyAnalysis.EXPORT_DATE_TIME)} ${chart.birthData.dateTime}")
                appendLine("${locManager.getString(StringKeyAnalysis.EXPORT_LOCATION)} ${chart.birthData.location}")
                appendLine("${locManager.getString(StringKeyAnalysis.EXPORT_COORDINATES)} ${formatCoordinate(chart.birthData.latitude.toDouble(), true)}, ${formatCoordinate(chart.birthData.longitude.toDouble(), false)}")
                appendLine("${locManager.getString(StringKeyAnalysis.EXPORT_TIMEZONE)}: ${chart.birthData.timezone}")
                appendLine()

                // Chart Summary
                appendLine(locManager.getString(StringKeyAnalysis.EXPORT_CHART_SUMMARY))
                appendLine("─────────────────────────────────────────────────────────────────────────────")
                val ascSign = ZodiacSign.fromLongitude(chart.ascendant)
                val moonPos = chart.planetPositions.find { it.planet == Planet.MOON }
                val sunPos = chart.planetPositions.find { it.planet == Planet.SUN }

                appendLine("${locManager.getString(StringKeyAnalysis.CHART_ASCENDANT_LAGNA)}: ${ascSign.displayName}")
                moonPos?.let { appendLine("${locManager.getString(StringKeyAnalysis.EXPORT_MOON_SIGN)}: ${it.sign.displayName}") }
                sunPos?.let { appendLine("${locManager.getString(StringKeyAnalysis.EXPORT_SUN_SIGN)}: ${it.sign.displayName}") }
                moonPos?.let { appendLine("${locManager.getString(StringKeyAnalysis.EXPORT_BIRTH_NAKSHATRA)}: ${it.nakshatra.displayName} (${locManager.getString(StringKeyAnalysis.PANCHANGA_PADA)} ${it.nakshatraPada})") }
                appendLine()

                // Planetary Positions
                append(chart.toPlainText())
                appendLine()

                // Yogas
                val yogaAnalysis = YogaCalculator.calculateYogas(chart)
                append(yogaAnalysis.toPlainText())
                appendLine()

                // Shadbala Summary
                val shadbala = ShadbalaCalculator.calculateShadbala(chart)
                append(shadbala.getSummaryInterpretation())
                appendLine()

                // Ashtakavarga Summary
                val ashtakavarga = AshtakavargaCalculator.calculateAshtakavarga(chart)
                append(ashtakavarga.toPlainText())
                appendLine()

                // Footer
                appendLine("═══════════════════════════════════════════════════════════════════════════════")
                appendLine("                    ${locManager.getString(StringKeyAnalysis.EXPORT_GENERATED_BY_SHORT)}")
                appendLine("                  ${locManager.getString(StringKeyAnalysis.EXPORT_ULTRA_PRECISION)}")
                appendLine("                  ${locManager.getString(StringKeyAnalysis.EXPORT_CALC_ENGINE)}")
                appendLine("═══════════════════════════════════════════════════════════════════════════════")
            }

            val fileName = "AstroStorm_${chart.birthData.name.replace(" ", "_")}_${dateFormatter.format(Date())}.txt"
            val path = saveTextFile(report, fileName)

            ExportResult.Success(path, ExportFormat.TEXT)
        } catch (e: Exception) {
            ExportResult.Error("Failed to export text report: ${e.message}")
        }
    }

    private fun saveTextFile(content: String, fileName: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/AstroStorm")
            }

            val uri = context.contentResolver.insert(
                MediaStore.Files.getContentUri("external"),
                contentValues
            ) ?: throw Exception("Failed to create file")

            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(content.toByteArray())
            }

            uri.toString()
        } else {
            @Suppress("DEPRECATION")
            val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val astroStormDir = File(documentsDir, "AstroStorm")
            if (!astroStormDir.exists()) astroStormDir.mkdirs()

            val file = File(astroStormDir, fileName)
            FileWriter(file).use { writer ->
                writer.write(content)
            }

            file.absolutePath
        }
    }

    // ==================== HELPER FUNCTIONS ====================

    private fun formatCoordinate(value: Double, isLatitude: Boolean): String {
        val abs = kotlin.math.abs(value)
        val degrees = abs.toInt()
        val minutes = ((abs - degrees) * 60).toInt()
        val seconds = ((((abs - degrees) * 60) - minutes) * 60).toInt()
        val direction = if (isLatitude) {
            if (value >= 0) "N" else "S"
        } else {
            if (value >= 0) "E" else "W"
        }
        return "$degrees° $minutes' $seconds\" $direction"
    }

    private fun formatDegree(degree: Double): String {
        val normalizedDegree = (degree % 360.0 + 360.0) % 360.0
        val deg = normalizedDegree.toInt()
        val min = ((normalizedDegree - deg) * 60).toInt()
        val sec = ((((normalizedDegree - deg) * 60) - min) * 60).toInt()
        return "$deg° $min' $sec\""
    }

    private fun isExalted(planet: Planet, sign: ZodiacSign): Boolean {
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.ARIES
            Planet.MOON -> sign == ZodiacSign.TAURUS
            Planet.MARS -> sign == ZodiacSign.CAPRICORN
            Planet.MERCURY -> sign == ZodiacSign.VIRGO
            Planet.JUPITER -> sign == ZodiacSign.CANCER
            Planet.VENUS -> sign == ZodiacSign.PISCES
            Planet.SATURN -> sign == ZodiacSign.LIBRA
            else -> false
        }
    }

    private fun isDebilitated(planet: Planet, sign: ZodiacSign): Boolean {
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.LIBRA
            Planet.MOON -> sign == ZodiacSign.SCORPIO
            Planet.MARS -> sign == ZodiacSign.CANCER
            Planet.MERCURY -> sign == ZodiacSign.PISCES
            Planet.JUPITER -> sign == ZodiacSign.CAPRICORN
            Planet.VENUS -> sign == ZodiacSign.VIRGO
            Planet.SATURN -> sign == ZodiacSign.ARIES
            else -> false
        }
    }
}

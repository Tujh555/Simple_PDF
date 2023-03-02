package ru.a1Estate.simplepdf.domain.pdfGenerating

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.text.Layout
import ru.a1Estate.simplepdf.domain.pdfGenerating.style.Border
import ru.a1Estate.simplepdf.domain.pdfGenerating.style.Margin
import ru.a1Estate.simplepdf.domain.pdfGenerating.style.Padding

abstract class Element(open val parent: Element?) {
    var border = Border.ZERO
    var margin = Margin.ZERO
    var padding = Padding.ZERO

    var backgroundColor: Int? = null
    var fontColor: Int? = null
    var fontSize: Float? = null
    var horizontalAlignment: Layout.Alignment? = null
    var font: Typeface? = null

    protected val document: Document by lazy {
        if (this is Document) {
            this
        } else {
            parent!!.document
        }
    }

    protected val inheritedBackgroundColor: Int by lazy {
        backgroundColor ?: parent?.inheritedBackgroundColor ?: FALLBACK_BACKGROUND_COLOR
    }
    protected val inheritedFontColor: Int by lazy {
        fontColor ?: parent?.inheritedFontColor ?: FALLBACK_FONT_COLOR
    }
    internal val inheritedFontSize: Float by lazy {
        fontSize ?: parent?.inheritedFontSize ?: FALLBACK_FONT_SIZE
    }
    internal val inheritedHorizontalAlignment: Layout.Alignment by lazy {
        horizontalAlignment ?: parent?.inheritedHorizontalAlignment ?: FALLBACK_HORIZONTAL_ALIGNMENT
    }
    internal val inheritedFont: Typeface by lazy {
        font ?: parent?.inheritedFont ?: FALLBACK_FONT
    }

    private var cachedInstanceHeightStartY: Int? = null
    private var cachedInstanceHeight: Int? = null

    fun height(width: Int, startY: Int, minHeight: Int = 0): Int {
        if (cachedInstanceHeight == null || cachedInstanceHeightStartY == null) {
            cachedInstanceHeight = instanceHeight(
                width = width - margin.start - margin.end - padding.start - padding.end,
                startY = startY + margin.top + padding.top
            )
            cachedInstanceHeightStartY = startY
        }

        return minHeight.coerceAtLeast(cachedInstanceHeight!!) + margin.top + margin.bottom + padding.top + padding.bottom
    }

    abstract fun instanceHeight(width: Int, startY: Int): Int

    open fun render(
        page: PdfDocument.Page,
        startX: Int,
        endX: Int,
        startY: Int,
        minHeight: Int = 0
    ) {
        renderInstance(
            page,
            startX = startX + margin.start + padding.start,
            endX = endX - margin.end - padding.end,
            startY = startY + margin.top + padding.top,
            minHeight = minHeight
        )

        val height = instanceHeight(
            width = endX - startX - margin.start - margin.end - padding.start - padding.end,
            startY = startY + margin.top + padding.top
        )

        border.drawBorder(
            page,
            startX = startX + margin.start,
            endX = endX - margin.end,
            startY = startY + margin.top,
            endY = startY + margin.top + minHeight.coerceAtLeast(padding.top + height + padding.bottom)
        )

        drawBox(
            page,
            startX = startX + margin.start,
            endX = endX - margin.end,
            startY = startY + margin.top,
            endY = startY + margin.top + minHeight.coerceAtLeast(padding.top + height + padding.bottom),
            color = inheritedBackgroundColor
        )
    }

    abstract fun renderInstance(
        page: PdfDocument.Page,
        startX: Int,
        endX: Int,
        startY: Int,
        minHeight: Int = 0
    )

    companion object {
        private const val FALLBACK_BACKGROUND_COLOR = Color.WHITE
        private const val FALLBACK_FONT_COLOR = Color.BLACK
        private const val FALLBACK_FONT_SIZE = 12f
        private val FALLBACK_HORIZONTAL_ALIGNMENT = Layout.Alignment.ALIGN_NORMAL
        private val FALLBACK_FONT = Typeface.SANS_SERIF
    }
}
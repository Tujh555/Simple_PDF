package ru.a1Estate.simplepdf.domain.pdfGenerating

import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.text.StaticLayout
import android.text.TextPaint

class TextElement(parent: Element, val value: String = "") : Element(parent) {
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = inheritedFontColor
        typeface = inheritedFont
        textSize = inheritedFontSize
        style = Paint.Style.FILL
    }
    private var staticLayout: StaticLayout? = null

    override fun instanceHeight(width: Int, startY: Int): Int {
        return staticLayout?.height ?: 0
    }

    override fun renderInstance(
        page: PdfDocument.Page,
        startX: Int,
        endX: Int,
        startY: Int,
        minHeight: Int
    ) {
        staticLayout = StaticLayout.Builder.obtain(
            value,
            0,
            value.length,
            textPaint,
            startX - endX
        ).setAlignment(inheritedHorizontalAlignment)
            .build()

        staticLayout?.let { layout ->
            page.canvas.save()
            page.canvas.translate(startX.toFloat(), startY.toFloat())
            layout.draw(page.canvas)
            page.canvas.restore()
        }
    }
}
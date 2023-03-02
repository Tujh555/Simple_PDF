package ru.a1Estate.simplepdf.domain.pdfGenerating.style

import android.graphics.Color
import android.graphics.pdf.PdfDocument
import ru.a1Estate.simplepdf.domain.pdfGenerating.drawLine

data class Border(
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0,
    val start: Int = 0,
    val topColor: Int = Color.BLACK,
    val endColor: Int = Color.BLACK,
    val bottomColor: Int = Color.BLACK,
    val startColor: Int = Color.BLACK
) {
    companion object {
        val ZERO = Border()
    }

    constructor(
        top: Int = 0,
        end: Int = 0,
        bottom: Int = 0,
        start: Int = 0,
        color: Int = Color.BLACK
    ) : this(top, end, bottom, start, color, color, color, color)

    constructor(
        width: Int = 0,
        color: Int = Color.BLACK
    ) : this(width, width, width, width, color)

    fun drawBorder(
        page: PdfDocument.Page,
        startX: Int,
        startY: Int,
        endX: Int,
        endY: Int,
    ) {
        drawLine(page, startX, startY, endX, endY, top, topColor)
        drawLine(page, startX, startY, endX, endY, start, startColor)
        drawLine(page, startX, startY, endX, endY, end, endColor)
        drawLine(page, startX, startY, endX, endY, bottom, bottomColor)
    }
}
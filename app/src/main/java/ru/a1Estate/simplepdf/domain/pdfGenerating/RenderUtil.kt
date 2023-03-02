package ru.a1Estate.simplepdf.domain.pdfGenerating

import android.graphics.Paint
import android.graphics.pdf.PdfDocument

fun drawLine(
    page: PdfDocument.Page,
    startX: Int,
    startY: Int,
    endX: Int,
    endY: Int,
    width: Int,
    color: Int
) {
    if (width == 0) {
        return
    } else if (width < 0) {
        throw IllegalArgumentException("drawLine() called with negative width")
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        this.strokeWidth = width.toFloat()
        this.color = color
    }

    val canvas = page.canvas
    val offset = width / 2
    canvas.drawLine(startX.toFloat(), startY - offset.toFloat(), endX.toFloat(), endY - offset.toFloat(), paint)
}

fun drawBox(
    page: PdfDocument.Page,
    startX: Int,
    endX: Int,
    startY: Int,
    endY: Int,
    color: Int
) {
    val width = endX - startX
    val height = endY - startY
    if (width <= 0 || height <= 0) {
        return
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        this.color = color
    }

    page.canvas.drawRect(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat(), paint)
}

fun getPageIndex(pageHeight: Int, y: Int): Int {
    var t = y
    var i = 0

    while (t >= pageHeight) {
        t -= pageHeight
        i++
    }

    return i
}

fun transformY(page: PdfDocument.Page, y: Int): Int {
    return page.info.pageHeight - (y - page.info.pageHeight * getPageIndex(page.info.pageHeight, y))
}
package ru.a1Estate.simplepdf.domain.pdfGenerating

import android.graphics.pdf.PdfDocument

class TableElement : Element(null) {
    override fun instanceHeight(width: Int, startY: Int): Int {
        return 1
    }

    override fun renderInstance(
        page: PdfDocument.Page,
        startX: Int,
        endX: Int,
        startY: Int,
        minHeight: Int
    ) {
    }
}
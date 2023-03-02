package ru.a1Estate.simplepdf.domain.pdfGenerating

import android.graphics.Canvas
import android.graphics.pdf.PdfDocument

class Document : Element(null) {
    private var pageNumber = 1
    val children = mutableListOf<Element>()

    var pageWidth: Int = 210
    var pageHeight: Int = 297
    var pdfDocument: PdfDocument? = null

    override fun instanceHeight(width: Int, startY: Int): Int = children.fold(0) { sum, row ->
        sum + row.height(width, startY)
    }

    fun render(): Canvas {
        val info = PdfDocument.PageInfo.Builder(
            pageWidth,
            pageHeight,
            pageNumber++
        ).create()
        pdfDocument?.startPage(info)?.let { page ->
            render(page, 0, pageWidth, 0, 0)
            return page.canvas
        }

        throw IllegalStateException("Document is not set ")
    }

    override fun renderInstance(
        page: PdfDocument.Page,
        startX: Int,
        endX: Int,
        startY: Int,
        minHeight: Int
    ) {
        children.fold(startY) { childStartY, child ->
            val adjustedStartY = when (child) {
                is TableElement -> childStartY
                else -> adjustStartYForPaging(childStartY, childStartY + child.height(endX - startX, startY))
            }

            child.render(page, startX, endX, adjustedStartY)
            adjustedStartY + child.height(endX - startX, adjustedStartY)
        }

    /*
     * TODO: render footer on each page
     *  - use footerFactory(page, totalPages) to construct footers
     *  - footers shouldn't impact number of pages
     *    - need to consume bottom of page or render inside margins?
     */
    }

    fun adjustStartYForPaging(startY: Int, endY: Int): Int {
        fun getYOffsetForPage(y: Int): Int {
            var t = y

            while (t >= document.pageHeight) {
                t -= document.pageHeight
            }
            return t
        }

        fun Int.insideBottomMargin() = getYOffsetForPage(this) > document.pageHeight - margin.top

        if (startY.insideBottomMargin()
            || endY.insideBottomMargin()
            || getPageIndex(pageHeight, startY) != getPageIndex(pageHeight, endY)
        ) {
            return (getPageIndex(pageHeight, startY) + 1) * pageHeight + margin.top
        }

        return startY
    }
}
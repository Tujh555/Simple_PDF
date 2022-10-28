package ru.a1Estate.simplepdf.domain.pdfCreating.drawers

import android.graphics.pdf.PdfDocument
import ru.a1Estate.simplepdf.domain.pdfCreating.settings.PageSettings

interface Drawer<T> {
    fun drawOnPage(page: PdfDocument.Page, settings: PageSettings, content: T)
}
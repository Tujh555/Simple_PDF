package ru.a1Estate.simplepdf.domain.pdfCreating

import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import kotlinx.coroutines.flow.Flow
import ru.a1Estate.simplepdf.domain.pdfCreating.settings.Padding
import ru.a1Estate.simplepdf.domain.pdfCreating.settings.PageSettings

/**
 * Class, that directly draws the pdf file
 */
interface PdfCreator {
    val document: PdfDocument

    /**
     * Method saves current page
     *
     * @return flow with saving state. It's can be Saving, Failure or Success
     * @see SavingState
     */
    fun savePage(): Flow<SavingState>

    /**
     * Method creates new page of current document
     *
     * @param settings PageSettings for new page
     */
    fun startNewPage(settings: PageSettings)
    fun addTitle(title: String)
    fun addImage(image: Bitmap, description: String)
    fun addFooter(footer: String)
    fun addPageNumber(add: Boolean, padding: Padding? = null)
}
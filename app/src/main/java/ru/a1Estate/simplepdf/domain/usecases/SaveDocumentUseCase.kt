package ru.a1Estate.simplepdf.domain.usecases

import android.graphics.pdf.PdfDocument

interface SaveDocumentUseCase {
    suspend operator fun invoke(document: PdfDocument, fileName: String)
}
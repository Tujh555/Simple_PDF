package ru.a1Estate.simplepdf.domain

import android.graphics.pdf.PdfDocument

interface Repository {
    suspend fun saveDocument(document: PdfDocument, fileName: String)
}
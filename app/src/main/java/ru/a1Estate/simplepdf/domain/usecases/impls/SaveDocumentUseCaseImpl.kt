package ru.a1Estate.simplepdf.domain.usecases.impls

import android.graphics.pdf.PdfDocument
import ru.a1Estate.simplepdf.domain.Repository
import ru.a1Estate.simplepdf.domain.usecases.SaveDocumentUseCase
import javax.inject.Inject

class SaveDocumentUseCaseImpl @Inject constructor(
    private val repository: Repository
) : SaveDocumentUseCase {
    override suspend fun invoke(document: PdfDocument, fileName: String) {
        repository.saveDocument(document, fileName)
    }
}
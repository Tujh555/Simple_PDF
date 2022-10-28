package ru.a1Estate.simplepdf.data

import android.graphics.pdf.PdfDocument
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.a1Estate.simplepdf.domain.Repository
import java.io.File
import java.io.FileOutputStream

class FileRepository(storagePath: String) : Repository {
    private val dir = File("$storagePath/MyPdf")
    val directoryPath: String
        get() = dir.absolutePath

    init {
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }

    override suspend fun saveDocument(document: PdfDocument, fileName: String) {
        withContext(Dispatchers.IO) {
            val pdfFIle = File(dir.absolutePath + "/$fileName.pdf")
            Log.d("MyLogs", pdfFIle.absolutePath)

            document.writeTo(FileOutputStream(pdfFIle))
            document.close()
        }
    }
}
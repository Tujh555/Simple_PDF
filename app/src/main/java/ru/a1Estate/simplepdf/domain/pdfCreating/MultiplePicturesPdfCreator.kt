package ru.a1Estate.simplepdf.domain.pdfCreating

import ru.a1Estate.simplepdf.domain.pdfCreating.content.buildingData.BuildingData

interface MultiplePicturesPdfCreator : PdfCreator {
    fun addAdditionalDescription(description: String)
    fun addStatement(statement: String, description: String)
    fun addMainStatements(statements: BuildingData)
}
package ru.a1Estate.simplepdf.domain.pdfCreating.content

import ru.a1Estate.simplepdf.domain.pdfCreating.content.buildingData.BuildingData

data class PageContentWithBuildStatements(
    val title: String,
    val footer: String,
    val mainContent: BuildingData
)
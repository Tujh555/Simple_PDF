package ru.a1Estate.simplepdf.domain.pdfCreating.content

import ru.a1Estate.simplepdf.domain.pdfCreating.content.buildingData.BuildingData

sealed class PageContent(
    open val title: String,
    open val footer: String
) {
    data class PageContentWithBuildingStatements(
        override val title: String,
        override val footer: String,
        val content: BuildingData
    ) : PageContent(title, footer)

    data class PageContentWithImages(
        override val title: String,
        override val footer: String,
        val content: List<ImageContent>
    ) : PageContent(title, footer)

    class Builder() {

    }
}
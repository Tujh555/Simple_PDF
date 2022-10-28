package ru.a1Estate.simplepdf.domain.pdfCreating.content

/**
 * Class contains content of one page
 */
data class PageContentWithImages(
    val title: String,
    val footer: String,
    val images: List<ImageContent>
) {
    class Builder {
        var title: String? = null
        var footer: String? = null
        private val images: MutableList<ImageContent> = mutableListOf()

        fun addImageContent(init: () -> ImageContent): Builder {
            images.add(init())

            return this
        }

        fun build(): PageContentWithImages {
            return PageContentWithImages(
                title = title ?: "",
                footer = footer ?: "",
                images = images.toList()
            )
        }
    }
}
package ru.a1Estate.simplepdf.domain.pdfCreating.content.buildingData

data class BuildingData(
    val district: String,
    val type: BuildingType,
    val floorsCount: Int,
    val renovation: Renovation,
    val documentPackage: DocumentPackage,
    val square: Float,
    val price: Float,
    val description: String
) {
    class Builder() {
        var district: String = ""
        var type: BuildingType = BuildingType.EMPTY
        var floorsCount: Int? = null
            set(value) {
                if (value == null || value <= 0) {
                    throw IllegalArgumentException("Floors count can't be less than 0")
                }

                field = value
            }
        var renovation: Renovation = Renovation.EMPTY
        var documentPackage: DocumentPackage = DocumentPackage.EMPTY
        var square: Float? = null
            set(value) {
                if (value == null || value <= 0) {
                    throw IllegalArgumentException("Square can't be less than 0")
                }

                field = value
            }
        var price: Float? = null
            set(value) {
                if (value == null || value <= 0) {
                    throw IllegalArgumentException("Price can't be less than 0")
                }

                field = value
            }
        var description: String = ""

        constructor(init: BuildingData.Builder.() -> Unit): this()

        fun build(): BuildingData {
            if (price == null || square == null || floorsCount == null) {
                throw IllegalStateException("Price, square and floorsCount must be initialized")
            }

            return BuildingData(
                district = district,
                type = type,
                floorsCount = floorsCount!!,
                renovation = renovation,
                documentPackage = documentPackage,
                square = square!!,
                price = price!!,
                description = description
            )
        }
    }
}
package ru.a1Estate.simplepdf.domain.pdfGenerating.style

data class Padding(
    val top: Int = 0,
    val end: Int = 0,
    val bottom: Int = 0,
    val start: Int = 0,
) {
    companion object {
        val ZERO = Padding()
    }
}

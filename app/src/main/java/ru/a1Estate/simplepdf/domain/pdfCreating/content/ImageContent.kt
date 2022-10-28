package ru.a1Estate.simplepdf.domain.pdfCreating.content

import android.graphics.Bitmap

data class ImageContent(
    val image: Bitmap,
    val description: String
)
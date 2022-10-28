package ru.a1Estate.simplepdf.domain.pdfCreating.settings

import android.text.Layout

/**
 * Sealed class for describing text in document
 *
 * @property padding padding in the text area, units PT
 * @property textSize text size in PT
 */
sealed class TextSettings(
    val textAlignment: Layout.Alignment,
    val padding: Padding,
    val textColor: Int,
    val textSize: Float
) {
    class TitleSettings(
        textAlignment: Layout.Alignment,
        padding: Padding,
        textColor: Int,
        textSize: Float
    ) : TextSettings(textAlignment, padding, textColor, textSize)

    class FooterSettings(
        textAlignment: Layout.Alignment,
        padding: Padding,
        textColor: Int,
        textSize: Float
    ) : TextSettings(textAlignment, padding, textColor, textSize)

    /**
     * Class for describe settings of pictures and their describes
     *
     * @property padding general padding of main content area, units is PT
     * @property descriptionPadding is padding in description area, units is PT
     * @property intervalBetweenImages is distance from one picture to the other, units is PT
     */
    class MainContentSettings(
        textAlignment: Layout.Alignment,
        padding: Padding,
        textColor: Int,
        textSize: Float,
        val imageAlignment: Layout.Alignment,
        val descriptionPadding: Int,
        val descriptionLocation: DescriptionLocation,
        val intervalBetweenImages: Float,
    ) : TextSettings(textAlignment, padding, textColor, textSize)
}
package ru.a1Estate.simplepdf.domain.pdfCreating.drawers.pictureDrawers

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.StaticLayout
import ru.a1Estate.simplepdf.domain.pdfCreating.content.ImageContent

class RightDescriptionDrawer(res: Resources) : OnePictureDrawer(res) {
    override fun Canvas.drawOnePicture(content: ImageContent) {
        val padding = settings.mainContentSettings.padding
        val paint = settings.paint.apply {
            textSize = settings.mainContentSettings.textSize
            color = settings.mainContentSettings.textColor
        }

        val dimensions = getScaledBitmapDimensions(content.image.height, content.image.width)
        val scaledHeight = dimensions.second
        val scaledWidth = dimensions.first

        val textPadding = settings.mainContentSettings.descriptionPadding

        val newBitmap =
            Bitmap.createScaledBitmap(content.image, scaledWidth, scaledHeight, true)
        drawBitmap(
            newBitmap,
            freeSpace.left + padding.start.toFloat(),
            freeSpace.top + padding.top.toFloat(),
            Paint(Paint.ANTI_ALIAS_FLAG)
        )

        val staticLayout = StaticLayout.Builder.obtain(
            content.description,
            0,
            content.description.length,
            paint,
            settings.pageWidth - padding.start - padding.end - newBitmap.width - textPadding * 2
        ).setAlignment(settings.mainContentSettings.textAlignment)
            .build()

        save()
        translate(
            freeSpace.left + padding.start + newBitmap.width + padding.end + textPadding.toFloat(),
            freeSpace.top + padding.top.toFloat()
        )

        staticLayout.draw(this)
        restore()
    }
}
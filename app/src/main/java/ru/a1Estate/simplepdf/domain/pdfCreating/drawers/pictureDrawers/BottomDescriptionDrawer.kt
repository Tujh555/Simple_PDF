package ru.a1Estate.simplepdf.domain.pdfCreating.drawers.pictureDrawers

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.StaticLayout
import ru.a1Estate.simplepdf.domain.pdfCreating.content.ImageContent
import kotlin.math.ceil

class BottomDescriptionDrawer(res: Resources) : OnePictureDrawer(res) {
    override fun Canvas.drawOnePicture(content: ImageContent) {
        val padding = settings.mainContentSettings.padding
        val paint = settings.paint.apply {
            textSize = settings.mainContentSettings.textSize
            color = settings.mainContentSettings.textColor
        }

        val dimensions = getScaledBitmapDimensions(content.image.height, content.image.width)
        var scaledHeight = dimensions.second
        var scaledWidth = dimensions.first
        val textPadding = settings.mainContentSettings.descriptionPadding

        var staticLayout = StaticLayout.Builder.obtain(
            content.description,
            0,
            content.description.length,
            paint,
            scaledWidth
        ).setAlignment(settings.mainContentSettings.textAlignment)
            .build()

        while (staticLayout.height + scaledHeight + padding.top + padding.bottom + textPadding > freeSpace.height()) {
            val reductionRatio =
                ceil((scaledHeight + staticLayout.height + padding.top + padding.bottom + textPadding - freeSpace.height()) / 2f).toInt()
            scaledWidth += reductionRatio
            scaledHeight -= reductionRatio

            staticLayout = StaticLayout.Builder.obtain(
                content.description,
                0,
                content.description.length,
                paint,
                scaledWidth
            ).setAlignment(settings.mainContentSettings.textAlignment)
                .build()
        }

        val newBitmap =
            Bitmap.createScaledBitmap(content.image, scaledWidth, scaledHeight, true)
        drawBitmap(
            newBitmap,
            freeSpace.left.toFloat() + padding.start,
            freeSpace.top.toFloat() + padding.top,
            Paint(Paint.ANTI_ALIAS_FLAG)
        )

        save()

        translate(
            freeSpace.left + padding.start.toFloat(),
            freeSpace.top + padding.top + padding.bottom + newBitmap.height + textPadding.toFloat()
        )
        staticLayout.draw(this)
        restore()
    }
}
package ru.a1Estate.simplepdf.domain.pdfCreating.drawers.pictureDrawers

import android.content.res.Resources
import android.graphics.Canvas
import android.text.Layout
import android.text.StaticLayout
import ru.a1Estate.simplepdf.domain.pdfCreating.content.ImageContent
import kotlin.math.ceil

abstract class OnePictureDrawer(res: Resources) : BaseImageDocumentDrawer(res) {

    override fun Canvas.drawMainContent(images: List<ImageContent>) {
        if (images.isNotEmpty()) {
            drawOnePicture(images.first())
        }
    }

    abstract fun Canvas.drawOnePicture(content: ImageContent)

    protected fun getScaledBitmapDimensions(oldHeight: Int, oldWidth: Int): Pair<Int, Int> {
        if (oldHeight * oldWidth == 0) return Pair(0, 0)

        val padding = settings.mainContentSettings.padding

        val heightCoefficient =
            ceil(oldHeight.toFloat() / (freeSpace.height() - padding.top - padding.bottom)).toInt()
        val widthCoefficient =
            ceil(oldWidth.toFloat() / (freeSpace.width() - padding.start - padding.end)).toInt()

        var h = oldHeight
        var w = oldWidth

        while (h % 10 == 0 && w % 10 == 0) {
            h /= 10
            w /= 10
        }

        return when {
            widthCoefficient > 1 && heightCoefficient > 1 -> {
                val maxCoefficient = maxOf(widthCoefficient, heightCoefficient)
                Pair(oldWidth / maxCoefficient, oldHeight / maxCoefficient)
            }

            heightCoefficient > 1 -> {
                val newHeight = oldHeight / heightCoefficient
                val newWidth = (newHeight / h) * w
                Pair(newWidth, newHeight)
            }

            widthCoefficient > 1 -> {
                val newWidth = oldWidth / widthCoefficient
                val newHeight = (newWidth / w) * h
                Pair(newWidth, newHeight)
            }

            else -> Pair(oldWidth, oldHeight)
        }
    }

    override fun Canvas.drawFooter(footer: String) {
        if (footer.isEmpty()) return

        val paint = settings.paint.apply {
            textSize = settings.footerSettings.textSize
            color = settings.footerSettings.textColor
        }
        val padding = settings.footerSettings.padding

        val staticLayout = StaticLayout.Builder.obtain(
            footer,
            0,
            footer.length,
            paint,
            settings.pageWidth - padding.start - padding.end
        ).setAlignment(settings.footerSettings.textAlignment)
            .build()

        freeSpace.bottom -= padding.bottom + padding.top + staticLayout.height

        val dy = settings.pageHeight - padding.bottom - staticLayout.height.toFloat()

        save()
        when (settings.footerSettings.textAlignment) {
            Layout.Alignment.ALIGN_CENTER -> {
                val dx =
                    (settings.pageWidth - padding.start - padding.end) / 2 - staticLayout.width / 2
                translate(dx.toFloat(), dy)
            }

            Layout.Alignment.ALIGN_NORMAL -> {
                translate(
                    padding.start.toFloat(),
                    dy
                )
            }

            Layout.Alignment.ALIGN_OPPOSITE -> {
                val dx = (settings.pageWidth - padding.end - staticLayout.width)
                translate(dx.toFloat(), dy)
            }
        }

        staticLayout.draw(this)

        restore()
    }

    override fun Canvas.drawTitle(title: String) {
        if (title.isEmpty()) return

        val paint = settings.paint.apply {
            textSize = settings.titleSettings.textSize
            color = settings.titleSettings.textColor
            isFakeBoldText = true
        }
        val padding = settings.titleSettings.padding

        val staticLayout = StaticLayout.Builder.obtain(
            title,
            0,
            title.length,
            paint,
            settings.pageWidth - padding.start - padding.end
        ).setAlignment(settings.titleSettings.textAlignment)
            .build()

        freeSpace.top += staticLayout.height + padding.top + padding.bottom

        save()

        when (settings.titleSettings.textAlignment) {
            Layout.Alignment.ALIGN_CENTER -> {
                val difference =
                    (settings.pageWidth - padding.start - padding.end - staticLayout.width) / 2
                translate(difference.toFloat(), padding.top.toFloat())
            }
            Layout.Alignment.ALIGN_NORMAL -> {
                translate(padding.start.toFloat(), padding.top.toFloat())
            }
            Layout.Alignment.ALIGN_OPPOSITE -> {
                val difference =
                    settings.pageWidth - padding.start - padding.end - staticLayout.width
                translate(difference.toFloat(), padding.top.toFloat())
            }
        }

        staticLayout.draw(this)
        restore()
    }

}
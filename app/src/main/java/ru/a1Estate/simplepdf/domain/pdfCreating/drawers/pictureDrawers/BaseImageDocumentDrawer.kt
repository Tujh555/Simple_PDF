package ru.a1Estate.simplepdf.domain.pdfCreating.drawers.pictureDrawers

import android.content.res.Resources
import android.graphics.*
import android.graphics.pdf.PdfDocument
import ru.a1Estate.simplepdf.domain.pdfCreating.content.ImageContent
import ru.a1Estate.simplepdf.domain.pdfCreating.content.PageContentWithImages
import ru.a1Estate.simplepdf.domain.pdfCreating.drawers.Drawer
import ru.a1Estate.simplepdf.domain.pdfCreating.settings.PageSettings

abstract class BaseImageDocumentDrawer(
    private val res: Resources
) : Drawer<PageContentWithImages> {
    private var currentSettings: PageSettings? = null
    protected val settings: PageSettings
        get() = requireNotNull(currentSettings) {
            "Settings were null"
        }
    
    protected var freeSpace = Rect(0, 0, 0, 0)

    final override fun drawOnPage(page: PdfDocument.Page, settings: PageSettings, content: PageContentWithImages) {
        freeSpace = Rect(
            0,
            0,
            settings.pageWidth,
            settings.pageHeight
        )
        currentSettings = settings

        page.canvas.run {
            settings.background?.let {
                val bitmap = BitmapFactory.decodeResource(res, it)
                drawBackground(bitmap)
            }

            drawTitle(content.title)
            drawFooter(content.footer)
            drawMainContent(content.images)
        }
    }

    private fun Canvas.drawBackground(bitmap: Bitmap) {
        val newBitmap = Bitmap.createScaledBitmap(bitmap, settings.pageWidth,settings.pageHeight, true)
        drawBitmap(newBitmap, 0f, 0f, Paint(Paint.ANTI_ALIAS_FLAG))
    }

    abstract fun Canvas.drawMainContent(images: List<ImageContent>)

    abstract fun Canvas.drawFooter(footer: String)

    abstract fun Canvas.drawTitle(title: String)
    
}
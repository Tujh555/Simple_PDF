package ru.a1Estate.simplepdf.domain.pdfCreating.cretators

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.a1Estate.simplepdf.domain.pdfCreating.PdfCreator
import ru.a1Estate.simplepdf.domain.pdfCreating.SavingState
import ru.a1Estate.simplepdf.domain.pdfCreating.content.ImageContent
import ru.a1Estate.simplepdf.domain.pdfCreating.content.PageContentWithImages
import ru.a1Estate.simplepdf.domain.pdfCreating.settings.Padding
import ru.a1Estate.simplepdf.domain.pdfCreating.settings.PageSettings
import javax.inject.Inject
import javax.inject.Provider

/**
 * Class, that directly draws the pdf file
 *
 * @property res android resources
 * @property builderFactory factory for builders content of page
 * @property document android PdfDocument
 */
class OnePicturePdfCreator @Inject constructor(
    private val res: Resources,
    private val builderFactory: Provider<PageContentWithImages.Builder>,
    override val document: PdfDocument,
) : PdfCreator {
    private var currentPageNumber = 1
    private var currentPage: PdfDocument.Page? = null
    private var contentBuilder: PageContentWithImages.Builder? = null
    private var isPageNumberNeed = false
    private var settings: PageSettings? = null

    //A square bounding the unoccupied space of the canvas
    private var freeSpace = Rect(0, 0, 0, 0)

    private val _settings: PageSettings
        get() = requireNotNull(settings) {
            "Settings wasn't set"
        }

    override fun startNewPage(settings: PageSettings) {
        val pageInfo = PdfDocument.PageInfo.Builder(
            settings.pageWidth,
            settings.pageHeight,
            currentPageNumber
        ).create()

        this.settings = settings
        contentBuilder = builderFactory.get()
        currentPage = document.startPage(pageInfo)
        freeSpace = Rect(
            0,
            0,
            _settings.pageWidth,
            _settings.pageHeight
        )
    }

    override fun savePage() = flow {
        emit(SavingState.Saving)

        currentPage?.let { page ->
            if (contentBuilder == null) {
                emit(
                    SavingState.Failure(IllegalStateException("Content builder was not initialized"))
                )

                return@flow
            }

            try {
                drawOnPage(page, contentBuilder!!.build())
            } catch (e: Exception) {
                emit(SavingState.Failure(e))
                return@flow
            }
            document.finishPage(page)
            currentPageNumber++
            emit(SavingState.Success(currentPageNumber))
        } ?: emit(
            SavingState.Failure(IllegalStateException("Current page was not initialized"))
        )
    }.flowOn(Dispatchers.Default)

    override fun addTitle(title: String) {
        contentBuilder?.title = title
    }

    override fun addImage(image: Bitmap, description: String) {
        contentBuilder?.addImageContent {
            ImageContent(image, description)
        }
    }

    override fun addFooter(footer: String) {
        contentBuilder?.footer = footer
    }

    override fun addPageNumber(add: Boolean, padding: Padding?) {
        isPageNumberNeed = add
    }

    private fun drawOnPage(page: PdfDocument.Page, content: PageContentWithImages) {
//        page.canvas.run {
//            _settings.background?.let {
//                val bitmap = BitmapFactory.decodeResource(res, it)
//                drawBackground(bitmap)
//            }
//
//            drawTitle(content.title)
//            drawFooter(content.footer)
//            drawMainContent(content.images)
//        }
    }
//
//    private fun Canvas.drawBackground(bitmap: Bitmap) {
//        val newBitmap = Bitmap.createScaledBitmap(bitmap, _settings.pageWidth,_settings.pageHeight, true)
//        drawBitmap(newBitmap, 0f, 0f, Paint(Paint.ANTI_ALIAS_FLAG))
//    }
//
//    private fun Canvas.drawTitle(title: String) {
//        val paint = _settings.paint.apply {
//            textSize = _settings.titleSettings.textSize
//            color = _settings.titleSettings.textColor
//            isFakeBoldText = true
//        }
//        val padding = _settings.titleSettings.padding
//
//        val staticLayout = StaticLayout.Builder.obtain(
//            title,
//            0,
//            title.length,
//            paint,
//            _settings.pageWidth - padding.start - padding.end
//        ).setAlignment(_settings.titleSettings.textAlignment)
//            .build()
//
//        freeSpace.top += staticLayout.height + padding.top + padding.bottom
//
//        save()
//
//        when (_settings.titleSettings.textAlignment) {
//            Layout.Alignment.ALIGN_CENTER -> {
//                val difference =
//                    (_settings.pageWidth - padding.start - padding.end - staticLayout.width) / 2
//                translate(difference.toFloat(), padding.top.toFloat())
//            }
//            Layout.Alignment.ALIGN_NORMAL -> {
//                translate(padding.start.toFloat(), padding.top.toFloat())
//            }
//            Layout.Alignment.ALIGN_OPPOSITE -> {
//                val difference =
//                    _settings.pageWidth - padding.start - padding.end - staticLayout.width
//                translate(difference.toFloat(), padding.top.toFloat())
//            }
//        }
//
//        staticLayout.draw(this)
//        restore()
//    }
//
//    private fun getScaledBitmapDimensions(oldHeight: Int, oldWidth: Int): Pair<Int, Int> {
//        if (oldHeight * oldWidth == 0) return Pair(0, 0)
//
//        val padding = _settings.mainContentSettings.padding
//
//        val heightCoefficient =
//            ceil(oldHeight.toFloat() / (freeSpace.height() - padding.top - padding.bottom)).toInt()
//        val widthCoefficient =
//            ceil(oldWidth.toFloat() / (freeSpace.width() - padding.start - padding.end)).toInt()
//
//        var h = oldHeight
//        var w = oldWidth
//
//        while (h % 10 == 0 && w % 10 == 0) {
//            h /= 10
//            w /= 10
//        }
//
//        return when {
//            widthCoefficient > 1 && heightCoefficient > 1 -> {
//                val maxCoefficient = maxOf(widthCoefficient, heightCoefficient)
//                Pair(oldWidth / maxCoefficient, oldHeight / maxCoefficient)
//            }
//
//            heightCoefficient > 1 -> {
//                val newHeight = oldHeight / heightCoefficient
//                val newWidth = (newHeight / h) * w
//                Pair(newWidth, newHeight)
//            }
//
//            widthCoefficient > 1 -> {
//                val newWidth = oldWidth / widthCoefficient
//                val newHeight = (newWidth / w) * h
//                Pair(newWidth, newHeight)
//            }
//
//            else -> Pair(oldWidth, oldHeight)
//        }
//    }
//
//    private fun Canvas.drawMainContent(list: List<ImageContent>) {
//        if (list.isEmpty()) return
//
//        val content = list.first()
//        val padding = _settings.mainContentSettings.padding
//        val paint = _settings.paint.apply {
//            textSize = _settings.mainContentSettings.textSize
//            color = _settings.mainContentSettings.textColor
//        }
//
//        val dimensions = getScaledBitmapDimensions(content.image.height, content.image.width)
//        var scaledHeight = dimensions.second
//        var scaledWidth = dimensions.first
//
//        val textPadding = _settings.mainContentSettings.descriptionPadding
//        when (_settings.mainContentSettings.descriptionLocation) {
//            DescriptionLocation.RIGHT -> {
//                val newBitmap =
//                    Bitmap.createScaledBitmap(content.image, scaledWidth, scaledHeight, true)
//                drawBitmap(
//                    newBitmap,
//                    freeSpace.left + padding.start.toFloat(),
//                    freeSpace.top + padding.top.toFloat(),
//                    Paint(Paint.ANTI_ALIAS_FLAG)
//                )
//
//                val staticLayout = StaticLayout.Builder.obtain(
//                    content.description,
//                    0,
//                    content.description.length,
//                    paint,
//                    _settings.pageWidth - padding.start - padding.end - newBitmap.width - textPadding * 2
//                ).setAlignment(_settings.mainContentSettings.textAlignment)
//                    .build()
//
//                save()
//                translate(
//                    freeSpace.left + padding.start + newBitmap.width + padding.end + textPadding.toFloat(),
//                    freeSpace.top + padding.top.toFloat()
//                )
//
//                staticLayout.draw(this)
//                restore()
//            }
//
//            DescriptionLocation.LEFT -> {
//                val newBitmap =
//                    Bitmap.createScaledBitmap(content.image, scaledWidth, scaledHeight, true)
//
//                TODO()
//            }
//
//            DescriptionLocation.BOTTOM -> {
//                var staticLayout = StaticLayout.Builder.obtain(
//                    content.description,
//                    0,
//                    content.description.length,
//                    paint,
//                    scaledWidth
//                ).setAlignment(_settings.mainContentSettings.textAlignment)
//                    .build()
//
//                while (staticLayout.height + scaledHeight + padding.top + padding.bottom + textPadding > freeSpace.height()) {
//                    val reductionRatio =
//                        ceil((scaledHeight + staticLayout.height + padding.top + padding.bottom + textPadding - freeSpace.height()) / 2f).toInt()
//                    scaledWidth += reductionRatio
//                    scaledHeight -= reductionRatio
//
//                    staticLayout = StaticLayout.Builder.obtain(
//                        content.description,
//                        0,
//                        content.description.length,
//                        paint,
//                        scaledWidth
//                    ).setAlignment(_settings.mainContentSettings.textAlignment)
//                        .build()
//                }
//
//                val newBitmap =
//                    Bitmap.createScaledBitmap(content.image, scaledWidth, scaledHeight, true)
//                drawBitmap(
//                    newBitmap,
//                    freeSpace.left.toFloat() + padding.start,
//                    freeSpace.top.toFloat() + padding.top,
//                    Paint(Paint.ANTI_ALIAS_FLAG)
//                )
//
//                save()
//
//                translate(
//                    freeSpace.left + padding.start.toFloat(),
//                    freeSpace.top + padding.top + padding.bottom + newBitmap.height + textPadding.toFloat()
//                )
//                staticLayout.draw(this)
//                restore()
//            }
//        }
//    }
//
//    private fun Canvas.drawFooter(footer: String) {
//        val paint = _settings.paint.apply {
//            textSize = _settings.footerSettings.textSize
//            color = _settings.footerSettings.textColor
//        }
//        val padding = _settings.footerSettings.padding
//
//        val staticLayout = StaticLayout.Builder.obtain(
//            footer,
//            0,
//            footer.length,
//            paint,
//            _settings.pageWidth - padding.start - padding.end
//        ).setAlignment(_settings.footerSettings.textAlignment)
//            .build()
//
//        freeSpace.bottom -= padding.bottom + padding.top + staticLayout.height
//
//        val dy = _settings.pageHeight - padding.bottom - staticLayout.height.toFloat()
//
//        save()
//        when (_settings.footerSettings.textAlignment) {
//            Layout.Alignment.ALIGN_CENTER -> {
//                val dx =
//                    (_settings.pageWidth - padding.start - padding.end) / 2 - staticLayout.width / 2
//                translate(dx.toFloat(), dy)
//            }
//
//            Layout.Alignment.ALIGN_NORMAL -> {
//                translate(
//                    padding.start.toFloat(),
//                    dy
//                )
//            }
//
//            Layout.Alignment.ALIGN_OPPOSITE -> {
//                val dx = (_settings.pageWidth - padding.end - staticLayout.width)
//                translate(dx.toFloat(), dy)
//            }
//        }
//
//        staticLayout.draw(this)
//
//        restore()
//    }
}
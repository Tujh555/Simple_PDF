package ru.a1Estate.simplepdf.di.modules

import android.graphics.pdf.PdfDocument
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.a1Estate.simplepdf.domain.pdfCreating.PdfCreator
import ru.a1Estate.simplepdf.domain.pdfCreating.content.PageContentWithImages
import ru.a1Estate.simplepdf.domain.pdfCreating.cretators.OnePicturePdfCreator

@Module
class PdfCreatingModule {
    @Provides
    fun provideContentBuilder() = PageContentWithImages.Builder()

    @Provides
    fun providePdfDocument() = PdfDocument()
}

@Module
interface PdfCreatingBindsModule {
    @Binds
    fun bindPdfCreator(creator: OnePicturePdfCreator): PdfCreator
}
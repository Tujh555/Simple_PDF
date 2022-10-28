package ru.a1Estate.simplepdf.di.modules

import dagger.Binds
import dagger.Module
import ru.a1Estate.simplepdf.domain.usecases.SaveDocumentUseCase
import ru.a1Estate.simplepdf.domain.usecases.impls.SaveDocumentUseCaseImpl

@Module
interface UseCasesModule {
    @Binds
    fun bindSaveDocumentUseCase(case: SaveDocumentUseCaseImpl): SaveDocumentUseCase
}
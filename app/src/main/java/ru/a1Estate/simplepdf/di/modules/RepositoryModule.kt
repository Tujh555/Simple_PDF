package ru.a1Estate.simplepdf.di.modules

import android.os.Environment
import dagger.Module
import dagger.Provides
import ru.a1Estate.simplepdf.data.FileRepository
import ru.a1Estate.simplepdf.domain.Repository

@Module
class RepositoryModule {
    @Provides
    fun bindFileRepository(): Repository {
        return FileRepository(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath
        )
    }
}
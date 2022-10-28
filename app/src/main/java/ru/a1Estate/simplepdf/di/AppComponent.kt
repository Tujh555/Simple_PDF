package ru.a1Estate.simplepdf.di

import android.content.Context
import android.content.res.Resources
import dagger.BindsInstance
import dagger.Component
import ru.a1Estate.simplepdf.di.modules.*
import ru.a1Estate.simplepdf.di.modules.viewModels.ViewModelModule
import ru.a1Estate.simplepdf.presentation.MainActivity
import ru.a1Estate.simplepdf.presentation.dialogs.ExplanationDialogFragment
import ru.a1Estate.simplepdf.presentation.mainScreen.MainFragment

@Component(
    modules = [
        PermissionModule::class,
        PdfCreatingModule::class,
        RepositoryModule::class,
        PdfCreatingBindsModule::class,
        ActivityContractsModule::class,
        ViewModelModule::class,
        UseCasesModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ExplanationDialogFragment)
    fun inject(fragment: MainFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun resources(res: Resources): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
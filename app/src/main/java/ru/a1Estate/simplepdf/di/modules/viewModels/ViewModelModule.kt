package ru.a1Estate.simplepdf.di.modules.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.a1Estate.simplepdf.presentation.ViewModelFactory
import ru.a1Estate.simplepdf.presentation.mainScreen.MainViewModel

@Module
interface ViewModelModule {
    @Binds
    fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @VIewModelKey(MainViewModel::class)
    fun bindMainViewModel(model: MainViewModel): ViewModel
}
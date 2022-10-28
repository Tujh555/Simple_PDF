package ru.a1Estate.simplepdf.di.modules.viewModels

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class VIewModelKey(val value: KClass<out ViewModel>)

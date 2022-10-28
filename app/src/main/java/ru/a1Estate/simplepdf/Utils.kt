package ru.a1Estate.simplepdf

import android.content.Context
import ru.a1Estate.simplepdf.di.AppComponent

val Context.appComponent: AppComponent
    get() = if (this is ru.a1Estate.simplepdf.App) {
        appComponent
    } else {
        applicationContext.appComponent
    }
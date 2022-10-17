package com.app.simplepdf

import android.content.Context
import com.app.simplepdf.di.AppComponent

val Context.appComponent: AppComponent
    get() = if (this is App) {
        appComponent
    } else {
        applicationContext.appComponent
    }
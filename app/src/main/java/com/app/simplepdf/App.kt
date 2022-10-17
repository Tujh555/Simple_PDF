package com.app.simplepdf

import android.app.Application
import com.app.simplepdf.di.AppComponent
import com.app.simplepdf.di.DaggerAppComponent

class App : Application() {
    private var _appComponent: AppComponent? = null
    val appComponent: AppComponent
        get() = requireNotNull(_appComponent) {
            "App component was null"
        }

    override fun onCreate() {
        super.onCreate()

        _appComponent = DaggerAppComponent.create()
    }
}
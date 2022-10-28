package ru.a1Estate.simplepdf

import android.app.Application
import ru.a1Estate.simplepdf.di.AppComponent

class App : Application() {
    private var _appComponent: AppComponent? = null
    val appComponent: AppComponent
        get() = requireNotNull(_appComponent) {
            "App component was null"
        }

    override fun onCreate() {
        super.onCreate()

        _appComponent = ru.a1Estate.simplepdf.di.DaggerAppComponent.builder()
            .context(applicationContext)
            .resources(resources)
            .build()
    }
}
package com.app.simplepdf.di

import com.app.simplepdf.di.modules.PermissionModule
import com.app.simplepdf.presentation.MainActivity
import com.app.simplepdf.presentation.dialogs.ExplanationDialogFragment
import com.app.simplepdf.presentation.mainScreen.MainFragment
import dagger.Component

@Component(
    modules = [
        PermissionModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ExplanationDialogFragment)
    fun inject(fragment: MainFragment)
}
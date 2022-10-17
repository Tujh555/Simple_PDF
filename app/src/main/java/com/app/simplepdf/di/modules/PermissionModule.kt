package com.app.simplepdf.di.modules

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.app.simplepdf.permissions.ManageStorageResultContract
import com.app.simplepdf.permissions.PermissionsManager
import com.app.simplepdf.presentation.MainActivity
import dagger.Module
import dagger.Provides

@Module
class PermissionModule {
    @Provides
    fun provideManageStorageContract() = ManageStorageResultContract()

    @Provides
    fun providePermissionManager(contract: ManageStorageResultContract): PermissionsManager {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            PermissionsManager.HighVersionPermissionManager(contract)
        } else {
            PermissionsManager.LowLevelManager()
        }
    }
}
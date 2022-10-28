package ru.a1Estate.simplepdf.di.modules

import android.os.Build
import dagger.Module
import dagger.Provides
import ru.a1Estate.simplepdf.permissions.ManageStorageResultContract
import ru.a1Estate.simplepdf.permissions.PermissionsManager

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
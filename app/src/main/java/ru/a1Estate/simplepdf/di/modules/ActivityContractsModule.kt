package ru.a1Estate.simplepdf.di.modules

import android.graphics.Bitmap
import androidx.activity.result.contract.ActivityResultContract
import dagger.Binds
import dagger.Module
import ru.a1Estate.simplepdf.domain.contracts.TakePhotoContact
import ru.a1Estate.simplepdf.permissions.ManageStorageResultContract

@Module
interface ActivityContractsModule {
    @Binds
    fun bindTakePhotoContract(contract: TakePhotoContact): ActivityResultContract<Unit?, Bitmap?>

    @Binds
    fun bindPermissionContract(contract: ManageStorageResultContract): ActivityResultContract<Unit?, Boolean>
}
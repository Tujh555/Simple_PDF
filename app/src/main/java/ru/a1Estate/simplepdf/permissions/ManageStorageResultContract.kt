package ru.a1Estate.simplepdf.permissions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import javax.inject.Inject

class ManageStorageResultContract @Inject constructor() : ActivityResultContract<Unit?, Boolean>() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun createIntent(context: Context, input: Unit?) = Intent(
        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
    ).apply {
        addCategory("android.intent.category.DEFAULT")
        data = Uri.parse("package:${context.packageName}")
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return Environment.isExternalStorageManager()
    }
}
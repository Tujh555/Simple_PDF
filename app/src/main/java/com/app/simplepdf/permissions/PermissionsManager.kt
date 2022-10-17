package com.app.simplepdf.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import javax.inject.Inject

sealed class PermissionsManager(
    protected val activity: AppCompatActivity,
    protected val contract: ManageStorageResultContract,
) {
    abstract val isPermissionGranted: Boolean
    protected var onRequestFinishCallback: ((Boolean) -> Unit)? = null
    abstract fun requestPermission()

    fun onRequestFinished(callback: (Boolean) -> Unit) {
        onRequestFinishCallback = callback
    }

    @RequiresApi(Build.VERSION_CODES.R)
    class HighVersionPermissionManager @Inject constructor (
        activity: AppCompatActivity,
        contract: ManageStorageResultContract
    ) : PermissionsManager(activity, contract) {
        override val isPermissionGranted: Boolean
            get() = Environment.isExternalStorageManager()

        private val getPermission = activity.registerForActivityResult(contract) {
            onRequestFinishCallback?.invoke(it)
        }

        override fun requestPermission() {
            getPermission.launch(null)
        }
    }

    class LowLevelManager @Inject constructor(
        activity: AppCompatActivity,
        contract: ManageStorageResultContract
    ) : PermissionsManager(activity, contract) {
        override val isPermissionGranted: Boolean
            get() = ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

        override fun requestPermission() {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_CODE
            )
        }

        companion object {
            private const val REQUEST_CODE = 101
        }
    }
}
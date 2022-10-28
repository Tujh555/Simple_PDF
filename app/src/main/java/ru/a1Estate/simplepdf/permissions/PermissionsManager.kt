package ru.a1Estate.simplepdf.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Class that encapsulates the work of obtaining permission.
 *
 * @property activity weak reference to activity
 * @property isPermissionGranted indicates whether permission has been received
 */
sealed class PermissionsManager {
    protected var onRequestFinishCallback: ((Boolean) -> Unit)? = null
    abstract val isPermissionGranted: Boolean

    var activity: WeakReference<FragmentActivity>? = null
        set(value) {
            if (field == null) {
                field = value
                onActivitySet()
            }
        }

    abstract fun requestPermission()
    abstract fun onActivitySet()

    /**
     * Adds callback for receiving permission
     */
    fun onRequestFinished(callback: (Boolean) -> Unit) {
        onRequestFinishCallback = callback
    }


    /**
     * Class requests manage storage permission with android version >= 30
     *
     * @param contract is result contract for activity result API
     */
    @RequiresApi(Build.VERSION_CODES.R)
    class HighVersionPermissionManager(
        private val contract: ActivityResultContract<Unit?, Boolean>
    ) : PermissionsManager() {
        override val isPermissionGranted: Boolean
            get() = Environment.isExternalStorageManager()

        private var getPermission: ActivityResultLauncher<Unit?>? = null

        override fun requestPermission() {
            getPermission?.launch(null)
        }

        override fun onActivitySet() {
            getPermission = activity?.get()?.let {
                it.registerForActivityResult(contract) { res ->
                    onRequestFinishCallback?.invoke(res)
                }
            } ?: throw IllegalStateException("Activity is not set")
        }
    }

    /**
     * Class requests manage storage permission with android version < 30
     */
    class LowLevelManager : PermissionsManager() {
        override val isPermissionGranted: Boolean
            get() = activity?.get()?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            } ?: throw IllegalStateException("Activity is not set")

        override fun requestPermission() {
            activity?.get()?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    REQUEST_CODE
                )
            } ?: throw IllegalStateException("Activity is not set")
        }

        override fun onActivitySet() { }

        companion object {
            private const val REQUEST_CODE = 101
        }
    }
}
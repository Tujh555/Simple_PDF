package ru.a1Estate.simplepdf.domain.contracts

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import java.io.IOException
import javax.inject.Inject

class TakePhotoContact @Inject constructor(
    private val context: Context,
) : ActivityResultContract<Unit?, Bitmap?>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bitmap? {
        return try {
            val uri: Uri = intent?.data ?: return null

            if (Build.VERSION.SDK_INT > 27) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }
        } catch (e: IOException) {
            null
        }
    }
}
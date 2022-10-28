package ru.a1Estate.simplepdf.domain.authentication

import android.net.Uri

object DriveScopes {
    private const val driveUrl = "https://www.googleapis.com/auth/drive.file"
    val driveUri = Uri.parse(driveUrl)
}
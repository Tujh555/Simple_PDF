package ru.a1Estate.simplepdf.domain.authentication

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task

class GoogleSignInActivityResultContract :
    ActivityResultContract<GoogleSignInClient, Task<GoogleSignInAccount>>() {
    override fun createIntent(context: Context, input: GoogleSignInClient) = input.signInIntent

    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount> {
        return GoogleSignIn.getSignedInAccountFromIntent(intent)
    }


}
package ru.a1Estate.simplepdf.presentation.authScreen

import android.app.Activity
import android.content.ContentResolver.MimeTypeInfo
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.FileContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.a1Estate.simplepdf.R
import ru.a1Estate.simplepdf.appComponent
import ru.a1Estate.simplepdf.data.FileRepository
import ru.a1Estate.simplepdf.databinding.FragmentDriveBinding
import ru.a1Estate.simplepdf.domain.Repository
import java.io.File
import java.util.Collections
import javax.inject.Inject

class DriveFragment : Fragment(R.layout.fragment_drive) {
    private val binding by viewBinding(FragmentDriveBinding::bind)
    private val drive by lazy { getNewDrive() }

    @Inject
    lateinit var repository: Repository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //accessDriveFiles()

        binding.btnUpload.setOnClickListener {
            uploadToDrive()
        }
    }

    private fun getNewDrive(): Drive {
        GoogleSignIn.getLastSignedInAccount(requireContext())?.let { googleAccount ->
            val credential = GoogleAccountCredential.usingOAuth2(
                requireContext(),
                listOf(DriveScopes.DRIVE_FILE)
            ).apply {
                selectedAccount = googleAccount.account
            }

            return Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                credential
            ).setApplicationName(getString(R.string.app_name))
                .build()
        }

        throw IllegalStateException("User must be signed in")
    }

    private fun uploadToDrive() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val dir = File(repository.directoryPath)

                val file = dir.listFiles()?.first()

                if (file == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "File is null", Toast.LENGTH_SHORT).show()
                    }

                    return@launch
                }

                val griveDir = com.google.api.services.drive.model.File().apply {
                    name = "Presentations folder"
                    this.mimeType = "application/vnd.google-apps.folder"
                }

                val folder = drive.files().create(griveDir)
                    .setFields("id")
                    .execute()

                val googleFile = com.google.api.services.drive.model.File().apply {
                    name = file.name
                    parents = Collections.singletonList(folder.id)
                }

                val mimeType = "application/pdf"
                val fileContent = FileContent(mimeType, file)

                val newFile = drive
                    .files()
                    .create(googleFile, fileContent)
                    .setFields("id, parents")
                    .execute()


                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "File ${newFile.id} created", Toast.LENGTH_SHORT).show()
                }
            } catch (e: UserRecoverableAuthIOException) {
                startActivityForResult(e.intent, 31)
            }
        }
    }

    private fun accessDriveFiles() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                var pageToken: String?

                do {
                    val result = drive.files()
                        .list()
                        .apply {
                            spaces = "drive"
                            fields = "nextPageToken, files(id, name)"
                            pageToken = this.pageToken
                        }.execute()

                    for (file in result) {
                        Log.d("MyLogs", "key = ${file.key}, value = ${file.value}")
                    }
                } while (pageToken != null)
            } catch (e: UserRecoverableAuthIOException) {
                startActivityForResult(e.intent, 31)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 31) {
            if (resultCode == Activity.RESULT_OK) {
                accessDriveFiles()
            }
        }
    }
}
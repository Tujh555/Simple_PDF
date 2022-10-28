package ru.a1Estate.simplepdf.presentation.authScreen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import ru.a1Estate.simplepdf.R
import ru.a1Estate.simplepdf.databinding.FragmentAuthenticationBinding
import ru.a1Estate.simplepdf.domain.authentication.GoogleSignInActivityResultContract

class AuthenticationFragment : Fragment(R.layout.fragment_authentication) {
    private val binding by viewBinding(FragmentAuthenticationBinding::bind)

    private val isUserSignIn: Boolean
        get() = GoogleSignIn.getLastSignedInAccount(requireContext()) != null

    private val getAuthData = registerForActivityResult(GoogleSignInActivityResultContract()) {
        Log.d("MyLogs", "Callback in fragment")
        it.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.tvText.text = "account = ${task.result.account}\n" + "displayName ${task.result.displayName}\n" + "Email ${task.result.email}"
                Toast.makeText(requireContext(), "Callback in fragment", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Something went wrong...", Toast.LENGTH_SHORT).show()
                Log.d("MyLogs", task.exception?.let { t -> "$t" } ?: "exception is null")
                task.exception?.printStackTrace()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val options = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        val client = GoogleSignIn.getClient(requireActivity(), options)

        binding.btnSignIn.setOnClickListener {
            getAuthData.launch(client)
        }
    }
}
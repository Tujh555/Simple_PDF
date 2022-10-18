package com.app.simplepdf.presentation.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.simplepdf.R
import com.app.simplepdf.appComponent
import com.app.simplepdf.databinding.FragmentDialogExplanationsBinding
import com.app.simplepdf.permissions.PermissionsManager
import java.lang.ref.WeakReference
import javax.inject.Inject

class ExplanationDialogFragment : DialogFragment() {
    private val binding by viewBinding(FragmentDialogExplanationsBinding::bind)

    @Inject
    lateinit var manager: PermissionsManager

    private val finishCallback: (Boolean) -> Unit = {
        Toast.makeText(
            requireContext(),
            if (it) "Разрешение получено" else "Разрешение отклонено",
            Toast.LENGTH_SHORT
        ).show()

        if (it) {
            binding.run {
                btnExit.visibility = View.VISIBLE

                btnExit.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    private val buttonOkClickListener = View.OnClickListener {
        manager.requestPermission()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
        manager.activity = WeakReference(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_explanations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOk.setOnClickListener(buttonOkClickListener)
        manager.onRequestFinished(finishCallback)
    }
}
package com.app.simplepdf.presentation.mainScreen

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.simplepdf.R
import com.app.simplepdf.appComponent
import com.app.simplepdf.databinding.FragmentMainBinding
import com.app.simplepdf.permissions.PermissionsManager
import com.app.simplepdf.presentation.dialogs.ExplanationDialogFragment
import java.lang.ref.WeakReference
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)

    @Inject
    lateinit var manager: PermissionsManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!manager.isPermissionGranted) {
            MainFragmentDirections.actionMainFragmentToExplanationDialogFragment().let {
                findNavController().navigate(it)
            }
        }
    }
}
package ru.a1Estate.simplepdf.presentation.mainScreen

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.simplepdf.presentation.mainScreen.MainFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.a1Estate.simplepdf.R
import ru.a1Estate.simplepdf.appComponent
import ru.a1Estate.simplepdf.databinding.FragmentMainBinding
import ru.a1Estate.simplepdf.domain.pdfCreating.SavingState
import ru.a1Estate.simplepdf.permissions.PermissionsManager
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)

    @Inject
    lateinit var manager: PermissionsManager

    @Inject
    lateinit var takePhotoContract: ActivityResultContract<Unit?, Bitmap?>

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainViewModel> { factory }

    private var takePhotoLauncher: ActivityResultLauncher<Unit?>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)

        takePhotoLauncher = registerForActivityResult(takePhotoContract) {
            it?.let { bitmap ->
                binding.btnGetImage.setImageBitmap(bitmap)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!manager.isPermissionGranted) {
            MainFragmentDirections.actionMainFragmentToExplanationDialogFragment().let {
                findNavController().navigate(it)
            }
        }

        binding.btnSavePage.setOnClickListener {
        }

        binding.btnSaveDocument.setOnClickListener {

        }

        binding.btnGetImage.setOnClickListener {
            takePhotoLauncher?.launch(null)
        }
    }

    private suspend fun changeUiState(it: SavingState) {
        withContext(Dispatchers.Main) {
            when (it) {
                is SavingState.Failure -> {
                    Log.e("MyLogs", it.exception.toString())
                    Toast.makeText(requireContext(), "Что то пошло не так...", Toast.LENGTH_SHORT)
                        .show()
                }
                is SavingState.Saving -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is SavingState.Success -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Страница ${it.pageNumber} сохранена",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
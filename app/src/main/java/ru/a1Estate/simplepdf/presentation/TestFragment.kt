package ru.a1Estate.simplepdf.presentation

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.a1Estate.simplepdf.R
import ru.a1Estate.simplepdf.appComponent
import ru.a1Estate.simplepdf.databinding.FragmentMainBinding
import ru.a1Estate.simplepdf.databinding.FragmentTestBinding
import ru.a1Estate.simplepdf.domain.Repository
import ru.a1Estate.simplepdf.makeToast
import javax.inject.Inject

class TestFragment : Fragment(R.layout.fragment_test) {
    private val binding by viewBinding(FragmentTestBinding::bind)
    @Inject
    lateinit var repository: Repository


    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
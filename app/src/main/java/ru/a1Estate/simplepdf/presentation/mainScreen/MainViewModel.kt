package ru.a1Estate.simplepdf.presentation.mainScreen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.a1Estate.simplepdf.domain.pdfCreating.PdfCreator
import ru.a1Estate.simplepdf.domain.pdfCreating.SavingState
import ru.a1Estate.simplepdf.domain.usecases.SaveDocumentUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val creator: PdfCreator,
    private val saveDocumentUseCase: SaveDocumentUseCase
) : ViewModel() {
    private var _savingFlow = MutableStateFlow<SavingState?>(null)
    val savingState: StateFlow<SavingState?>
        get() = _savingFlow.asStateFlow()

    fun addTitle(title: String) {
        creator.addTitle(title)
    }

    fun addFooter(footer: String) {
        creator.addFooter(footer)
    }

    fun addImage(image: Bitmap, description: String) {
        creator.addImage(
            image.copy(Bitmap.Config.ARGB_8888, false),
            description
        )
    }

    fun saveDocument(fileName: String) {
        viewModelScope.launch {
            saveDocumentUseCase(creator.document, fileName)
        }
    }

    fun savePage() {
        viewModelScope.launch {
            creator.savePage().collect {
                _savingFlow.emit(it)
            }
        }
    }
}
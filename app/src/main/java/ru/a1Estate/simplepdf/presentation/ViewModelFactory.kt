package ru.a1Estate.simplepdf.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val map: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return map[modelClass]?.get() as? T
            ?: throw IllegalStateException("ViewModel $modelClass not found")
    }
}
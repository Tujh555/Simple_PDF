package ru.a1Estate.simplepdf.domain.pdfCreating

sealed class SavingState {
    object Saving : SavingState()
    class Success(val pageNumber: Int) : SavingState()
    class Failure(val exception: Exception) : SavingState()
}
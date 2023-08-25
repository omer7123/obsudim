package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.CheckIfPsychologistUseCase
import com.example.mypsychologist.domain.useCase.DeleteAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(private val checkIfPsychologistUseCase: CheckIfPsychologistUseCase) : ViewModel() {
    private val _isPsychologist: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val isPsychologist: StateFlow<Boolean>
        get() = _isPsychologist.asStateFlow()

    fun checkIfPsychologist() {
        viewModelScope.launch {
            _isPsychologist.value = checkIfPsychologistUseCase()
        }
    }

    class Factory @Inject constructor(private val checkIfPsychologistUseCase: CheckIfPsychologistUseCase) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(checkIfPsychologistUseCase) as T
        }
    }
}
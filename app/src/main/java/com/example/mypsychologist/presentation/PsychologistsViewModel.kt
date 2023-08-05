package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.GetPsychologistsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PsychologistsViewModel(private val getPsychologistsUseCase: GetPsychologistsUseCase) :
    ViewModel() {

    private val _screenState: MutableStateFlow<PsychologistsScreenState> =
        MutableStateFlow(PsychologistsScreenState.Init)
    val screenState: StateFlow<PsychologistsScreenState>
        get() = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            _screenState.value = PsychologistsScreenState.Data(getPsychologistsUseCase())
        }
    }

    class Factory @Inject constructor(private val getPsychologistsUseCase: GetPsychologistsUseCase) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PsychologistsViewModel(getPsychologistsUseCase) as T
        }
    }
}
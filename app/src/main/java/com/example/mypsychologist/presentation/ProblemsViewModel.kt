package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mypsychologist.domain.useCase.GetProblemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ProblemsViewModel(getProblemsUseCase: GetProblemsUseCase) : ViewModel() {

    private val _screenState: MutableStateFlow<ProblemsScreenState> =
        MutableStateFlow(ProblemsScreenState.Init)
    val screenState: StateFlow<ProblemsScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = ProblemsScreenState.Data(getProblemsUseCase())
    }

    class Factory @Inject constructor(private val getProblemsUseCase: GetProblemsUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProblemsViewModel(getProblemsUseCase) as T
        }
    }
}
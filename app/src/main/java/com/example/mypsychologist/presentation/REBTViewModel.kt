package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mypsychologist.domain.useCase.GetREBTProblemProgressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class REBTViewModel(
    getREBTProblemProgressUseCase: GetREBTProblemProgressUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<REBTScreenState> =
        MutableStateFlow(REBTScreenState.Init)
    val screenState: StateFlow<REBTScreenState>
        get() =
            _screenState.asStateFlow()

    init {
        _screenState.value = REBTScreenState.Data(getREBTProblemProgressUseCase("Проблема"))
    }

    class Factory @Inject constructor(private val getREBTProblemProgressUseCase: GetREBTProblemProgressUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return REBTViewModel(getREBTProblemProgressUseCase) as T
        }
    }
}
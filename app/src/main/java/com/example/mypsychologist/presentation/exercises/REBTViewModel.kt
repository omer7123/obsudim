package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mypsychologist.domain.useCase.GetCurrentREBTProblemProgressUseCase
import com.example.mypsychologist.domain.useCase.GetREBTProblemProgressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class REBTViewModel(
    getCurrentREBTProblemProgressUseCase: GetCurrentREBTProblemProgressUseCase,
    private val getREBTProblemProgressUseCase: GetREBTProblemProgressUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<REBTScreenState> =
        MutableStateFlow(REBTScreenState.Init)
    val screenState: StateFlow<REBTScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = REBTScreenState.Data(getCurrentREBTProblemProgressUseCase())
    }

    fun getProblemProgress(problemId: Int) {
        _screenState.value = REBTScreenState.Data(getREBTProblemProgressUseCase(problemId))
    }

    class Factory @Inject constructor(
        private val getCurrentREBTProblemProgressUseCase: GetCurrentREBTProblemProgressUseCase,
        private val getREBTProblemProgressUseCase: GetREBTProblemProgressUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return REBTViewModel(
                getCurrentREBTProblemProgressUseCase,
                getREBTProblemProgressUseCase
            ) as T
        }
    }
}
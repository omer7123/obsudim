package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.GetCurrentREBTProblemProgressUseCase
import com.example.mypsychologist.domain.useCase.GetREBTProblemProgressUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        _screenState.value = REBTScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val currentProblem = getCurrentREBTProblemProgressUseCase()
            _screenState.value =
                if (currentProblem != null)
                    REBTScreenState.Data(currentProblem)
                else
                    REBTScreenState.Empty
        }
    }

    fun getProblemProgress(problemId: String) {
        _screenState.value = REBTScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = REBTScreenState.Data(getREBTProblemProgressUseCase(problemId)!!)
        }
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
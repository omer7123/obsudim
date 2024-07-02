package com.example.mypsychologist.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.SendAnswerToRequestUseCase
import com.example.mypsychologist.presentation.psychologist.AnswerScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClientRequestViewModel(private val sendAnswerToRequestUseCase: SendAnswerToRequestUseCase) :
    ViewModel() {

    private val _screenState: MutableStateFlow<AnswerScreenState> =
        MutableStateFlow(AnswerScreenState.Init)
    val screenState: StateFlow<AnswerScreenState>
        get() = _screenState.asStateFlow()

    fun sendAnswer(accept: Boolean, clientId: String) {
        _screenState.value = AnswerScreenState.Loading

        viewModelScope.launch {
//            _screenState.value =
//                AnswerScreenState.Response(sendAnswerToRequestUseCase(accept, clientId))
        }
    }

    class Factory @Inject constructor(private val sendAnswerToRequestUseCase: SendAnswerToRequestUseCase) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ClientRequestViewModel(sendAnswerToRequestUseCase) as T
        }
    }
}
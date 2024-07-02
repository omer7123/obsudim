package com.example.mypsychologist.presentation.psychologist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.SendRequestToPsychologistUseCase
import com.example.mypsychologist.presentation.main.FeedbackScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RequestToPsychologistViewModel(
    private val sendRequestToPsychologistUseCase: SendRequestToPsychologistUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<FeedbackScreenState> =
        MutableStateFlow(FeedbackScreenState.Init)
    val screenState: StateFlow<FeedbackScreenState>
        get() = _screenState.asStateFlow()

    fun tryToSendRequest(text: String, psychologistId: String) {
//        if (text.isEmpty())
//            _screenState.value = FeedbackScreenState.ValidationError
//        else
//            viewModelScope.launch {
//                _screenState.value = FeedbackScreenState.Response(
//                    sendRequestToPsychologistUseCase(
//                        psychologistId,
//                        text
//                    )
//                )
//            }

    }

    class Factory @Inject constructor(private val sendRequestToPsychologistUseCase: SendRequestToPsychologistUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return RequestToPsychologistViewModel(sendRequestToPsychologistUseCase) as T
        }
    }
}
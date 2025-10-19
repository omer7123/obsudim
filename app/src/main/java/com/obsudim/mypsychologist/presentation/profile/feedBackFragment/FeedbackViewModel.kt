package com.obsudim.mypsychologist.presentation.profile.feedBackFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedbackViewModel() : ViewModel() {

    private val _state: MutableStateFlow<FeedbackScreenState> =
        MutableStateFlow(FeedbackScreenState.Init)
    val state: StateFlow<FeedbackScreenState>
        get() = _state.asStateFlow()

    fun tryToSendFeedback(text: String) {
        if (text.isEmpty()) {
            _state.value = FeedbackScreenState.ValidationError
        } else {
            viewModelScope.launch {
//                _state.value = FeedbackScreenState.Response(sendFeedbackUseCase(text))
            }
        }
    }

    class Factory @Inject constructor() :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return FeedbackViewModel() as T
        }
    }
}
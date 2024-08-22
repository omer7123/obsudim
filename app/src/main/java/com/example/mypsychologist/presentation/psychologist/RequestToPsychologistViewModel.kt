package com.example.mypsychologist.presentation.psychologist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.SendRequestToPsychologistEntity
import com.example.mypsychologist.domain.useCase.SendRequestToPsychologistUseCase
import com.example.mypsychologist.domain.useCase.profile.GetOwnDataUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.psychologistsUseCases.SendRequestToManagerUseCase
import com.example.mypsychologist.presentation.main.FeedbackScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RequestToPsychologistViewModel(
    private val sendRequestToPsychologistUseCase: SendRequestToManagerUseCase,
    private val getOwnDataUseCase: GetOwnDataUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<FeedbackScreenState> =
        MutableStateFlow(FeedbackScreenState.Init)
    val screenState: StateFlow<FeedbackScreenState>
        get() = _screenState.asStateFlow()

    fun getUserData() {
        _screenState.value = FeedbackScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            renderUserData(getOwnDataUseCase())
        }
    }

    private fun renderUserData(response: Resource<ClientInfoEntity>) {
        _screenState.value = when (response) {
            is Resource.Loading -> FeedbackScreenState.Loading
            is Resource.Success -> FeedbackScreenState.UserNameSaved(response.data.name.isNotEmpty())
            is Resource.Error -> {
                if (response.msg == "Некорректный токен: 1 validation error for UserData\nbirth_date\n  Input should be a valid date [type=date_type, input_value=None, input_type=NoneType]\n    For further information visit https://errors.pydantic.dev/2.8/v/date_type")
                    FeedbackScreenState.UserNameSaved(false)
                else
                    FeedbackScreenState.Response(false)
            }
        }
    }

    fun tryToSendRequest(text: String, psychologistId: String) {
        if (text.isEmpty())
            _screenState.value = FeedbackScreenState.ValidationError
        else {
            viewModelScope.launch {
                when (val result = sendRequestToPsychologistUseCase(
                    SendRequestToPsychologistEntity(
                        psychologistId,
                        text
                    )
                )) {
                    is Resource.Error -> {}
                    Resource.Loading -> _screenState.value = FeedbackScreenState.Loading
                    is Resource.Success -> {
                        if (result.data == "Successfully")
                            _screenState.value = FeedbackScreenState.Response(true)
                        else
                            _screenState.value = FeedbackScreenState.Response(false)
                    }
                }
            }
        }
    }

    class Factory @Inject constructor(
        private val sendRequestToPsychologistUseCase: SendRequestToManagerUseCase,
        private val getOwnDataUseCase: GetOwnDataUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return RequestToPsychologistViewModel(
                sendRequestToPsychologistUseCase,
                getOwnDataUseCase
            ) as T
        }
    }
}
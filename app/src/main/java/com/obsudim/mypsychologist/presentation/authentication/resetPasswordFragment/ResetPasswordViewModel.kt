package com.obsudim.mypsychologist.presentation.authentication.resetPasswordFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.ResetPasswordEntity
import com.obsudim.mypsychologist.domain.useCase.authenticationUseCases.ResetPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {
    private val _screenState: MutableStateFlow<ResetPasswordScreenState> =
        MutableStateFlow(ResetPasswordScreenState.Content())

    val screenState: StateFlow<ResetPasswordScreenState> = _screenState.asStateFlow()

    fun sendRequestToResetPassword(){
        val currentState = screenState.value as ResetPasswordScreenState.Content

        viewModelScope.launch {
            resetPasswordUseCase(ResetPasswordEntity(currentState.email)).collect { state->
                when(state){
                    is Resource.Error<Unit> -> {
                        _screenState.value = ResetPasswordScreenState.Error
                    }
                    Resource.Loading -> {
                        _screenState.value = currentState.copy(isLoading = true)
                    }
                    is Resource.Success<Unit> -> {
                        _screenState.value = ResetPasswordScreenState.SuccessRequest
                    }
                }
            }
        }
    }

    fun changeEmail(email: String) {
        val currentState = screenState.value as ResetPasswordScreenState.Content
        _screenState.value = currentState.copy(email = email)
    }
}
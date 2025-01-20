package com.example.mypsychologist.presentation.authentication.authFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.useCase.authenticationUseCases.AuthWithDataUserUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.SaveTokenUseCase
import com.example.mypsychologist.domain.useCase.authenticationUseCases.SaveUserIdUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authWithDataUserUseCase: AuthWithDataUserUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase
) : ViewModel() {

    private val _stateScreen: MutableStateFlow<AuthContent> = MutableStateFlow(AuthContent())
    val stateScreen: StateFlow<AuthContent> = _stateScreen


    private val handler = CoroutineExceptionHandler { _, error ->
        _stateScreen.value = _stateScreen.value.copy(error = error.toString())
    }

    fun auth() {
        val email = _stateScreen.value.email
        val password = _stateScreen.value.password
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch(handler) {
                _stateScreen.value = _stateScreen.value.copy(loading = true)
                delay(3000)
                _stateScreen.value = _stateScreen.value.copy(loading = true, error = null)
                when (val result = authWithDataUserUseCase(AuthModel(email, password))) {
                    is Resource.Error -> _stateScreen.value = _stateScreen.value.copy(error = result.msg.toString(), loading = false)
                    Resource.Loading -> _stateScreen.value = _stateScreen.value.copy(error = null, loading = true)
                    is Resource.Success -> saveToken(result.data)
                }
            }
        }
    }

    fun emailChange(email: String){
        _stateScreen.value = _stateScreen.value.copy(email = email)
    }

    fun passwordChange(password: String){
        _stateScreen.value = _stateScreen.value.copy(password = password)
    }

    private suspend fun saveToken(result: User) {
        _stateScreen.value = _stateScreen.value.copy(loading = true)
        saveTokenUseCase(result.token)
        saveUserIdUseCase(result.user_id)
        _stateScreen.value = _stateScreen.value.copy(success = true)
    }
    fun removeError() {
        _stateScreen.value = _stateScreen.value.copy(error = null)
    }
}
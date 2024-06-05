package com.example.mypsychologist.presentation.authentication.authFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.entity.authenticationEntity.Auth
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases.AuthWithDataUserUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases.SaveTokenUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authWithDataUserUseCase: AuthWithDataUserUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {

    private val _stateScreen: MutableLiveData<AuthState> =
        MutableLiveData(AuthState.Initial)

    val stateScreen: LiveData<AuthState> = _stateScreen

    private val handler = CoroutineExceptionHandler { _, error ->
        when (error) {
            is IllegalArgumentException -> {
                _stateScreen.value =
                    AuthState.Error(error.toString())
            }

            is NullPointerException -> {
                _stateScreen.value =
                    AuthState.Error(error.toString())
            }

            else -> {
                throw error
            }
        }
    }

    fun auth(auth: AuthModel) {
        if (auth.email.isNotEmpty() && auth.password.isNotEmpty()) {
            viewModelScope.launch {
                when (val result = authWithDataUserUseCase(auth)) {
                    is Resource.Error -> _stateScreen.value = AuthState.Error(result.msg.toString())
                    Resource.Loading -> _stateScreen.value = AuthState.Loading
                    is Resource.Success -> saveToken(result.data)
                }
            }
        }else{
            _stateScreen.value = AuthState.Content(
                email = auth.email.isEmpty(),
                password = auth.password.isEmpty(),
            )
        }
    }

    private fun saveToken(result: User) {
        viewModelScope.launch(handler) {
            _stateScreen.value = AuthState.Loading
            saveTokenUseCase(result.token)
            _stateScreen.value = AuthState.Success
        }
    }


}
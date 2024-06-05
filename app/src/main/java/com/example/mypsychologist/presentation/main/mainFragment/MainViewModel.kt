package com.example.mypsychologist.presentation.main.mainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.useCase.CheckIfPsychologistUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases.AuthByTokenUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases.GetTokenUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases.SaveTokenUseCase
import com.example.mypsychologist.presentation.authentication.authFragment.AuthState
import com.example.mypsychologist.presentation.authentication.registrationFragment.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val checkIfPsychologistUseCase: CheckIfPsychologistUseCase,
    private val authByTokenUseCase: AuthByTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {
    private val _isPsychologist: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    private val _screenState: MutableLiveData<MainScreenState> =
        MutableLiveData(MainScreenState.Initial)
    val screenState: LiveData<MainScreenState> get() = _screenState

    val isPsychologist: StateFlow<Boolean>
        get() = _isPsychologist.asStateFlow()

    fun checkIfPsychologist() {
        viewModelScope.launch {
            _isPsychologist.value = checkIfPsychologistUseCase()
        }
    }

    fun authByToken() {
        viewModelScope.launch {
            _screenState.value = MainScreenState.Loading
            val token = getTokenUseCase.invoke()
            if (token == "")
                _screenState.value = MainScreenState.Error("Ошибка авторизации")
            else {
                when (val result = authByTokenUseCase.invoke(Token(token))) {
                    is Resource.Error -> _screenState.value = MainScreenState.Error(result.msg.toString())
                    Resource.Loading -> _screenState.value = MainScreenState.Loading
                    is Resource.Success -> {
                        saveTokenUseCase.invoke(result.data.token)
                        _screenState.value = MainScreenState.Success
                    }
                }

            }
        }
    }

    class Factory @Inject constructor(
        private val checkIfPsychologistUseCase: CheckIfPsychologistUseCase,
        private val authByTokenUseCase: AuthByTokenUseCase,
        private val getTokenUseCase: GetTokenUseCase,
        private val saveTokenUseCase: SaveTokenUseCase
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                checkIfPsychologistUseCase,
                authByTokenUseCase = authByTokenUseCase,
                getTokenUseCase = getTokenUseCase,
                saveTokenUseCase
            ) as T
        }
    }
}
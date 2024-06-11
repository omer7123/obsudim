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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val checkIfPsychologistUseCase: CheckIfPsychologistUseCase,

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


    class Factory @Inject constructor(
        private val checkIfPsychologistUseCase: CheckIfPsychologistUseCase,
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                checkIfPsychologistUseCase,
            ) as T
        }
    }
}
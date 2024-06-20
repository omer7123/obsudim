package com.example.mypsychologist.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditViewModel(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val changePhoneUseCase: ChangePhoneUseCase,
    private val getClientDataUseCase: GetClientDataUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<EditScreenState> =
        MutableStateFlow(EditScreenState.Init)
    val screenState: StateFlow<EditScreenState>
        get() = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
        }
    }

    fun changeBirthday(new: Long) {
        viewModelScope.launch {
        }
    }

    fun changeDiagnosis(new: String) {
        viewModelScope.launch {
        }
    }

    fun changeGender(new: String) {
        viewModelScope.launch {
        }
    }

    fun changeName(new: String) {
        viewModelScope.launch {
        }
    }

    fun changePassword(new: String) {
        viewModelScope.launch {
            _screenState.value = EditScreenState.Loading
            _screenState.value = EditScreenState.Response(changePasswordUseCase(new))
        }
    }

    fun changePhone(new: String) {
        viewModelScope.launch {
            _screenState.value = EditScreenState.Loading
            _screenState.value = EditScreenState.Response(changePhoneUseCase(new))
        }
    }

    fun changeRequest(new: List<String>) {
        viewModelScope.launch {
        }
    }

    class Factory @Inject constructor(
        private val changePasswordUseCase: ChangePasswordUseCase,
        private val changePhoneUseCase: ChangePhoneUseCase,
        private val getClientDataUseCase: GetClientDataUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EditViewModel(
                changePasswordUseCase, changePhoneUseCase, getClientDataUseCase
            ) as T
        }
    }
}
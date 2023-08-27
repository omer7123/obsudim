package com.example.mypsychologist.presentation

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
    private val changeBirthdayUseCase: ChangeBirthdayUseCase,
    private val changeDiagnosisUseCase: ChangeDiagnosisUseCase,
    private val changeGenderUseCase: ChangeGenderUseCase,
    private val changeNameUseCase: ChangeNameUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val changePhoneUseCase: ChangePhoneUseCase,
    private val changeRequestUseCase: ChangeRequestUseCase,
    private val getClientDataUseCase: GetClientDataUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<EditScreenState> =
        MutableStateFlow(EditScreenState.Init)
    val screenState: StateFlow<EditScreenState>
        get() = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            _screenState.value = EditScreenState.Loading
            _screenState.value = EditScreenState.CurrentData(getClientDataUseCase())
        }
    }

    fun changeBirthday(new: Long) {
        viewModelScope.launch {
            _screenState.value = EditScreenState.Loading
            _screenState.value = EditScreenState.Response(changeBirthdayUseCase(new))
        }
    }

    fun changeDiagnosis(new: String) {
        viewModelScope.launch {
            _screenState.value = EditScreenState.Loading
            _screenState.value = EditScreenState.Response(changeDiagnosisUseCase(new))
        }
    }

    fun changeGender(new: String) {
        viewModelScope.launch {
            _screenState.value = EditScreenState.Loading
            _screenState.value = EditScreenState.Response(changeGenderUseCase(new))
        }
    }

    fun changeName(new: String) {
        viewModelScope.launch {
            _screenState.value = EditScreenState.Response(changeNameUseCase(new))
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
        _screenState.value = EditScreenState.Loading
        viewModelScope.launch {
            _screenState.value = EditScreenState.Response(changeRequestUseCase(new))
        }
    }

    class Factory @Inject constructor(
        private val changeBirthdayUseCase: ChangeBirthdayUseCase,
        private val changeDiagnosisUseCase: ChangeDiagnosisUseCase,
        private val changeGenderUseCase: ChangeGenderUseCase,
        private val changeNameUseCase: ChangeNameUseCase,
        private val changePasswordUseCase: ChangePasswordUseCase,
        private val changePhoneUseCase: ChangePhoneUseCase,
        private val changeRequestUseCase: ChangeRequestUseCase,
        private val getClientDataUseCase: GetClientDataUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EditViewModel(
                changeBirthdayUseCase, changeDiagnosisUseCase, changeGenderUseCase, changeNameUseCase,
                changePasswordUseCase, changePhoneUseCase, changeRequestUseCase, getClientDataUseCase
            ) as T
        }
    }
}
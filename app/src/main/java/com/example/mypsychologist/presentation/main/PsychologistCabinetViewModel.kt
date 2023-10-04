package com.example.mypsychologist.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.GetClientsRequestsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PsychologistCabinetViewModel(private val getClientRequestsUseCase: GetClientsRequestsUseCase) :
    ViewModel() {

    private val _screenState: MutableStateFlow<PsychologistCabinetScreenState> =
        MutableStateFlow(PsychologistCabinetScreenState.Init)
    val screenState: StateFlow<PsychologistCabinetScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = PsychologistCabinetScreenState.Loading
        getRequests()
    }

    fun getRequests() {
        viewModelScope.launch {
            _screenState.value = PsychologistCabinetScreenState.Data(getClientRequestsUseCase())
        }
    }

    class Factory @Inject constructor(private val getClientRequestsUseCase: GetClientsRequestsUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PsychologistCabinetViewModel(getClientRequestsUseCase) as T
        }
    }
}
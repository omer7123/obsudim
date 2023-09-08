package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.GetClientsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClientsViewModel(private val getClientsUseCase: GetClientsUseCase) : ViewModel() {

    private val _screenState: MutableStateFlow<ClientsScreenState> =
        MutableStateFlow(ClientsScreenState.Init)
    val screenState: StateFlow<ClientsScreenState>
        get() = _screenState.asStateFlow()

    init {
        loadClients()
    }

    private fun loadClients() {
        _screenState.value = ClientsScreenState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = try {
                ClientsScreenState.Data(getClientsUseCase())
            } catch (t: Throwable) {
                ClientsScreenState.Error
            }
        }
    }

    class Factory @Inject constructor(private val getClientsUseCase: GetClientsUseCase) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ClientsViewModel(getClientsUseCase) as T
        }
    }
}
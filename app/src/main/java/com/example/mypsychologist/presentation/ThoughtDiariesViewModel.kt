package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.GetThoughtDiariesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ThoughtDiariesViewModel(private val getThoughtDiariesUseCase: GetThoughtDiariesUseCase) :
    ViewModel() {

    private val _screenState: MutableStateFlow<ThoughtDiariesScreenState> =
        MutableStateFlow(ThoughtDiariesScreenState.Init)

    val screenState: StateFlow<ThoughtDiariesScreenState>
        get() = _screenState.asStateFlow()

    fun loadDiaries() {
        _screenState.value = ThoughtDiariesScreenState.Loading

        viewModelScope.launch {
            _screenState.value = ThoughtDiariesScreenState.Data(getThoughtDiariesUseCase())
        }
    }

    class Factory @Inject constructor(private val getThoughtDiariesUseCase: GetThoughtDiariesUseCase) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ThoughtDiariesViewModel(getThoughtDiariesUseCase) as T
        }
    }
}
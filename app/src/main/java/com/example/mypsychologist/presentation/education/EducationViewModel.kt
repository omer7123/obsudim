package com.example.mypsychologist.presentation.education

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.useCase.*
import com.example.mypsychologist.presentation.ListScreenState
import com.example.mypsychologist.ui.education.toDelegateItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EducationViewModel(private val getEducationTopicsUseCase: GetEducationTopicsUseCase) :
    ViewModel() {

    private val _screenState: MutableStateFlow<ListScreenState> =
        MutableStateFlow(ListScreenState.Init)
    val screenState: StateFlow<ListScreenState>
        get() = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            _screenState.value = try {
                ListScreenState.Data(getEducationTopicsUseCase().toDelegateItems())
            } catch (t: Throwable) {
                ListScreenState.Error
            }
        }
    }

    class Factory @Inject constructor(
        private val getEducationTopicsUseCase: GetEducationTopicsUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EducationViewModel(getEducationTopicsUseCase) as T
        }
    }
}
package com.example.mypsychologist.presentation.education

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.useCase.retrofitUseCase.educationUseCases.GetAllThemeUseCase
import com.example.mypsychologist.presentation.ListScreenState
import com.example.mypsychologist.ui.education.toDelegateItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EducationTopicsViewModel(
    private val getAllThemeUseCase: GetAllThemeUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<ListScreenState> =
        MutableStateFlow(ListScreenState.Init)
    val screenState: StateFlow<ListScreenState>
        get() = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            _screenState.value = ListScreenState.Loading
            when(val res = getAllThemeUseCase()){
                is Resource.Error -> _screenState.value = ListScreenState.Error
                Resource.Loading -> _screenState.value = ListScreenState.Loading
                is Resource.Success -> _screenState. value = ListScreenState.Data(res.data.toDelegateItems())
            }
        }
    }

    class Factory @Inject constructor(
        private val getAllThemeUseCase: GetAllThemeUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EducationTopicsViewModel(getAllThemeUseCase) as T
        }
    }
}
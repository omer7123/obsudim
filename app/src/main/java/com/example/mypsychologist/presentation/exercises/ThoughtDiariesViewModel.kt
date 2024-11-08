package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.DiaryRecordEntity
import com.example.mypsychologist.domain.useCase.GetThoughtDiariesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ThoughtDiariesViewModel (
    private val getThoughtDiariesUseCase: GetThoughtDiariesUseCase,
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<Resource<List<DiaryRecordEntity>>> =
        MutableStateFlow(Resource.Loading)

    val screenState: StateFlow<Resource<List<DiaryRecordEntity>>>
        get() = _screenState.asStateFlow()

    fun loadDiaries() {
        _screenState.value = Resource.Loading

        viewModelScope.launch {
            _screenState.value = getThoughtDiariesUseCase()
        }
    }

    class Factory @Inject constructor(private val getThoughtDiariesUseCase: GetThoughtDiariesUseCase): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ThoughtDiariesViewModel(
                getThoughtDiariesUseCase,
            ) as T
        }
    }

}
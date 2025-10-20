package com.obsudim.mypsychologist.presentation.education.educationTopicsFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.useCase.educationUseCases.GetAllThemeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EducationTopicsViewModel(
    private val getAllThemeUseCase: GetAllThemeUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<TopicsScreenState> =
        MutableStateFlow(TopicsScreenState.Initial)
    val screenState: StateFlow<TopicsScreenState>
        get() = _screenState.asStateFlow()

    fun getTopics(){
        viewModelScope.launch {
            _screenState.value = TopicsScreenState.Loading
            when(val res = getAllThemeUseCase()){
                is Resource.Error -> {
                    Log.e("Error", res.msg.toString())
                    _screenState.value =
                        TopicsScreenState.Error(res.msg.toString())
                }
                Resource.Loading -> _screenState.value = TopicsScreenState.Loading
                is Resource.Success -> {
                    _screenState. value = TopicsScreenState.Content(res.data)
                }
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
package com.example.mypsychologist.presentation.main.mainFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyExerciseEntity
import com.example.mypsychologist.domain.useCase.exerciseUseCases.GetAllDailyExercisesUseCase
import com.example.mypsychologist.presentation.core.BaseStateUI
import com.example.mypsychologist.presentation.core.collectRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAllDailyExerciseEntity: GetAllDailyExercisesUseCase

) : ViewModel() {
    private val _screenState: MutableStateFlow<BaseStateUI<List<DailyExerciseEntity>>> =
        MutableStateFlow(BaseStateUI.Initial())
    val screenState: StateFlow<BaseStateUI<List<DailyExerciseEntity>>>
        get() = _screenState.asStateFlow()

    fun getAllExercises(){
        _screenState.value = BaseStateUI.Loading()
        viewModelScope.launch {
            getAllDailyExerciseEntity().collectRequest(_screenState)
        }
    } 


    class Factory @Inject constructor(
        private val getAllDailyExerciseEntity: GetAllDailyExercisesUseCase
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                getAllDailyExerciseEntity,
            ) as T
        }
    }
}
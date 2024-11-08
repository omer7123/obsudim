package com.example.mypsychologist.presentation.exercises.exercisesFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseEntity
import com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases.GetAllExercisesUseCase
import com.example.mypsychologist.presentation.core.BaseStateUI
import com.example.mypsychologist.presentation.core.collectRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class REBTViewModel(
    private val getAllExercisesUseCase: GetAllExercisesUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<BaseStateUI<List<ExerciseEntity>>> =
        MutableStateFlow(BaseStateUI.Initial())
    val screenState: StateFlow<BaseStateUI<List<ExerciseEntity>>>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = BaseStateUI.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            getAllExercisesUseCase().collectRequest(_screenState)
        }
    }

    class Factory @Inject constructor(
        private val getAllExercisesUseCase: GetAllExercisesUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return REBTViewModel(
                getAllExercisesUseCase
            ) as T
        }
    }
}
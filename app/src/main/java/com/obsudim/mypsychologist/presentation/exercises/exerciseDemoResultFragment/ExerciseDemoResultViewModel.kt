package com.obsudim.mypsychologist.presentation.exercises.exerciseDemoResultFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailResultEntity
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetExerciseDetailResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExerciseDemoResultViewModel @Inject constructor(private val getExerciseDetailResultUseCase: GetExerciseDetailResultUseCase) :
    ViewModel() {
    private val _screenState: MutableStateFlow<ExerciseDemoResultScreenState> = MutableStateFlow(ExerciseDemoResultScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    fun getExerciseResult(idExercise: String, idResult: String) {
        viewModelScope.launch {
            getExerciseDetailResultUseCase(idExercise, idResult).collect { state ->
                when (state) {
                    is Resource.Error<ExerciseDetailResultEntity> -> {
                        _screenState.value = ExerciseDemoResultScreenState.Error
                    }
                    Resource.Loading -> {
                        _screenState.value = ExerciseDemoResultScreenState.Loading
                    }
                    is Resource.Success<ExerciseDetailResultEntity> -> {
                        _screenState.value = ExerciseDemoResultScreenState.Content(
                            state.data.sections
                        )
                    }
                }
            }
        }
    }
}
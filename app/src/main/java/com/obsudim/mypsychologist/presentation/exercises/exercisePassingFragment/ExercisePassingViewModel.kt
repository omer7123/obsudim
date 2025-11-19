package com.obsudim.mypsychologist.presentation.exercises.exercisePassingFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailEntity
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetExerciseDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExercisePassingViewModel @Inject constructor(
    private val getExerciseDetailUseCase: GetExerciseDetailUseCase
): ViewModel() {
    private var _screenState =
        MutableStateFlow<ExercisePassingScreenState>(ExercisePassingScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    fun getExerciseStructure(id: String) {
        viewModelScope.launch {
            getExerciseDetailUseCase(id).collect { res ->
                when (res) {
                    is Resource.Error<ExerciseDetailEntity> -> _screenState.value =
                        ExercisePassingScreenState.Error

                    Resource.Loading -> _screenState.value = ExercisePassingScreenState.Loading
                    is Resource.Success<ExerciseDetailEntity> -> _screenState.value =
                        ExercisePassingScreenState.Content(0, res.data.pages)
                }
            }
        }
    }
}
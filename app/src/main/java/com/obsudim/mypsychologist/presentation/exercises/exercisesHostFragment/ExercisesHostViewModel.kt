package com.obsudim.mypsychologist.presentation.exercises.exercisesHostFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.ExerciseInfoPreviewEntity
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetExerciseInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExercisesHostViewModel @Inject constructor(private val getExerciseInfoUseCase: GetExerciseInfoUseCase) :
    ViewModel() {
    private val _screenState: MutableStateFlow<ExerciseHostScreenState> =
        MutableStateFlow(ExerciseHostScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    fun getInfo(id: String) {
        viewModelScope.launch {
            getExerciseInfoUseCase(id).collect { state ->
                when (state) {
                    is Resource.Error<ExerciseInfoPreviewEntity> -> {
                        _screenState.update {
                            ExerciseHostScreenState.Error
                        }
                    }

                    Resource.Loading -> {
                        _screenState.update {
                            ExerciseHostScreenState.Loading
                        }
                    }

                    is Resource.Success<ExerciseInfoPreviewEntity> -> {
                        _screenState.update {
                            ExerciseHostScreenState.Content(state.data)
                        }
                    }
                }
            }
        }
    }
}
package com.obsudim.mypsychologist.presentation.exercises.exercisesFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetAllExercisesUseCase
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetAllStatusExerciseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExercisesViewModel(
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val getAllStatusExerciseUseCase: GetAllStatusExerciseUseCase,
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<ExercisesScreenState> =
        MutableStateFlow(ExercisesScreenState.Init)
    val screenState: StateFlow<ExercisesScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = ExercisesScreenState.Content(emptyList())
        viewModelScope.launch {
            getAllExercisesUseCase().collect{resource->
                when(resource){
                    is Resource.Error -> _screenState.value = ExercisesScreenState.Error
                    Resource.Loading -> _screenState.value = ExercisesScreenState.Loading
                    is Resource.Success -> _screenState.value = ExercisesScreenState.Content(resource.data)
                }
            }
        }
    }

    class Factory @Inject constructor(
        private val getAllExercisesUseCase: GetAllExercisesUseCase,
        private val getAllStatusExerciseUseCase: GetAllStatusExerciseUseCase,
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ExercisesViewModel(
                getAllExercisesUseCase,
                getAllStatusExerciseUseCase
            ) as T
        }
    }
}
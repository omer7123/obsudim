package com.obsudim.mypsychologist.presentation.exercises.exercisesFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetAllExercisesUseCase
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetAllStatusExerciseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ExercisesViewModel(
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val getAllStatusExerciseUseCase: GetAllStatusExerciseUseCase,
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<ExercisesStatusScreenState> =
        MutableStateFlow(ExercisesStatusScreenState.Initial)
    val screenState: StateFlow<ExercisesStatusScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = ExercisesStatusScreenState.Content(emptyList())
//        viewModelScope.launch {
//            getAllStatusExerciseUseCase().collect{resource->
//                when(resource){
//                    is Resource.Error -> _screenState.value = ExercisesStatusScreenState.Error
//                    Resource.Loading -> _screenState.value = ExercisesStatusScreenState.Loading
//                    is Resource.Success -> _screenState.value = ExercisesStatusScreenState.Content(resource.data)
//                }
//            }
//        }
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
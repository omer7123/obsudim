package com.example.mypsychologist.presentation.exercises.exercisesFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.useCase.GetCurrentREBTProblemProgressUseCase
import com.example.mypsychologist.domain.useCase.GetREBTProblemProgressUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases.GetAllExercisesUseCase
import com.example.mypsychologist.presentation.exercises.ExercisesScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class REBTViewModel(
    getCurrentREBTProblemProgressUseCase: GetCurrentREBTProblemProgressUseCase,
    private val getREBTProblemProgressUseCase: GetREBTProblemProgressUseCase,
    private val getAllExercisesUseCase: GetAllExercisesUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<ExercisesScreenState> =
        MutableStateFlow(ExercisesScreenState.Init)
    val screenState: StateFlow<ExercisesScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = ExercisesScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getAllExercisesUseCase.invoke().collect{resource->
                when(resource){
                    is Resource.Error -> _screenState.value = ExercisesScreenState.Error(resource.msg.toString())
                    Resource.Loading -> _screenState.value = ExercisesScreenState.Loading
                    is Resource.Success -> _screenState.value = ExercisesScreenState.Data(resource.data)
                }

            }
        }
    }

    class Factory @Inject constructor(
        private val getCurrentREBTProblemProgressUseCase: GetCurrentREBTProblemProgressUseCase,
        private val getREBTProblemProgressUseCase: GetREBTProblemProgressUseCase,
        private val getAllExercisesUseCase: GetAllExercisesUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return REBTViewModel(
                getCurrentREBTProblemProgressUseCase,
                getREBTProblemProgressUseCase,
                getAllExercisesUseCase
            ) as T
        }
    }
}
package com.example.mypsychologist.presentation.exercises.diariesFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity
import com.example.mypsychologist.domain.useCase.exerciseUseCases.GetAllExerciseResultsUseCase
import com.example.mypsychologist.extensions.convertLondonTimeToDeviceTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ThoughtDiariesViewModel (
    private val getAllExerciseResultsUseCase: GetAllExerciseResultsUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<ThoughtDiariesScreenState> =
        MutableStateFlow(ThoughtDiariesScreenState.Initial)
    val screenState: StateFlow<ThoughtDiariesScreenState>
        get() = _screenState.asStateFlow()

    fun loadDiaries(exerciseId: String) {
        _screenState.value = ThoughtDiariesScreenState.Loading
        viewModelScope.launch {
            getAllExerciseResultsUseCase(exerciseId).collect{resource->
                when(resource){
                    is Resource.Error -> _screenState.value = ThoughtDiariesScreenState.Error
                    Resource.Loading -> Unit
                    is Resource.Success -> _screenState.value = ThoughtDiariesScreenState.Content(
                        resource.data.map {
                            RecordExerciseEntity(
                                it.completedExerciseId,
                                "",
                                it.date.convertLondonTimeToDeviceTime()
                            )
                        }
                    )
                }
            }
        }
    }

    class Factory @Inject constructor(private val getAllExerciseResultsUseCase: GetAllExerciseResultsUseCase): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ThoughtDiariesViewModel(
                getAllExerciseResultsUseCase,
            ) as T
        }
    }

}
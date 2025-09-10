package com.example.mypsychologist.presentation.exercises.recordsExerciseFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.exerciseEntity.RecordExerciseEntity
import com.example.mypsychologist.domain.useCase.exerciseUseCases.GetAllExerciseResultsUseCase
import com.example.mypsychologist.domain.useCase.exerciseUseCases.definitionProblemGroupExercise.GetAllDPGResultsUseCase
import com.example.mypsychologist.extensions.convertLondonTimeToDeviceTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecordsExerciseViewModel (
    private val getAllExerciseResultsUseCase: GetAllExerciseResultsUseCase,
    private val getAllDPGResultsUseCase: GetAllDPGResultsUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<RecordsExerciseScreenState> =
        MutableStateFlow(RecordsExerciseScreenState.Initial)
    val screenState: StateFlow<RecordsExerciseScreenState>
        get() = _screenState.asStateFlow()

    fun loadDiaries(exerciseId: String) {
        _screenState.value = RecordsExerciseScreenState.Loading
        viewModelScope.launch {
            getAllExerciseResultsUseCase(exerciseId).collect{resource->
                when(resource){
                    is Resource.Error -> _screenState.value = RecordsExerciseScreenState.Error
                    Resource.Loading -> _screenState.value = RecordsExerciseScreenState.Loading
                    is Resource.Success -> _screenState.value = RecordsExerciseScreenState.Content(
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

    fun loadDiariesDPG(){
        viewModelScope.launch {
            getAllDPGResultsUseCase().collect{resource->
                when(resource){
                    is Resource.Error -> {
                        Log.e("Error", resource.msg.toString())
                        _screenState.value = RecordsExerciseScreenState.Error
                    }
                        Resource.Loading -> _screenState.value = RecordsExerciseScreenState.Loading
                    is Resource.Success -> {
                        _screenState.value = RecordsExerciseScreenState.Content(
                            resource.data
                        )
                    }
                }
            }
        }
    }

    class Factory @Inject constructor(
        private val getAllExerciseResultsUseCase: GetAllExerciseResultsUseCase,
        private val getAllDPGResultsUseCase: GetAllDPGResultsUseCase
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return RecordsExerciseViewModel(
                getAllExerciseResultsUseCase,
                getAllDPGResultsUseCase
            ) as T
        }
    }

}
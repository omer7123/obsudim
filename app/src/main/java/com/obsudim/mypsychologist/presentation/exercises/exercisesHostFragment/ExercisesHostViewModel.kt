package com.obsudim.mypsychologist.presentation.exercises.exercisesHostFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetAllExerciseResultsUseCase
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.GetExerciseInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExercisesHostViewModel @Inject constructor(
    private val getExerciseInfoUseCase: GetExerciseInfoUseCase,
    private val getAllExerciseResultsUseCase: GetAllExerciseResultsUseCase
) :
    ViewModel() {
    private val _screenState: MutableStateFlow<ExerciseHostScreenState> =
        MutableStateFlow(ExerciseHostScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    fun getHistory(id: String) {
        viewModelScope.launch {
            combine(
                getExerciseInfoUseCase(id),
                getAllExerciseResultsUseCase(id)
            ) { infoState, historyState ->
                when {
                    infoState is Resource.Loading || historyState is Resource.Loading -> {
                        ExerciseHostScreenState.Loading
                    }

                    infoState is Resource.Error || historyState is Resource.Error -> {
                        Log.e("Error", (historyState as Resource.Error).msg.toString())
                        ExerciseHostScreenState.Error
                    }

                    infoState is Resource.Success && historyState is Resource.Success -> {
                        val historyData = historyState.data
                        ExerciseHostScreenState.Content(infoState.data, historyData)

                    }

                    else -> ExerciseHostScreenState.Initial
                }
            }.collect { state ->
                _screenState.value = state
            }
        }
    }
}
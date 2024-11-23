package com.example.mypsychologist.presentation.exercises.diariesFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseResultFromAPIEntity
import com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases.GetAllExerciseResultsUseCase
import com.example.mypsychologist.presentation.core.BaseStateUI
import com.example.mypsychologist.presentation.core.collectRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ThoughtDiariesViewModel (
    private val getAllExerciseResultsUseCase: GetAllExerciseResultsUseCase
) :
    ViewModel() {

    private val _screenState: MutableStateFlow<BaseStateUI<List<ExerciseResultFromAPIEntity>>> =
        MutableStateFlow(BaseStateUI.Initial())

    val screenState: StateFlow<BaseStateUI<List<ExerciseResultFromAPIEntity>>>
        get() = _screenState.asStateFlow()

    fun loadDiaries(exerciseId: String) {
        _screenState.value = BaseStateUI.Loading()
        viewModelScope.launch {
            getAllExerciseResultsUseCase(exerciseId).collectRequest(_screenState)
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
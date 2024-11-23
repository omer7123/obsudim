package com.example.mypsychologist.presentation.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.exerciseEntity.ExerciseDetailResultEntity
import com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases.GetExerciseDetailResultUseCase
import com.example.mypsychologist.presentation.core.BaseStateUI
import com.example.mypsychologist.presentation.core.collectRequest
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThoughtDiaryViewModel @AssistedInject constructor(
    private val getExerciseDetailResultUseCase: GetExerciseDetailResultUseCase,
    @Assisted("id") private val id: String,
//    @Assisted("clientId") private val clientId: String
) : ViewModel() {

    private val _screenState: MutableStateFlow<BaseStateUI<ExerciseDetailResultEntity>> =
        MutableStateFlow(BaseStateUI.Initial())
    val screenState: StateFlow<BaseStateUI<ExerciseDetailResultEntity>>
        get() = _screenState.asStateFlow()



    fun loadDiary(idResult: String) {
       // _screenState.value = ThoughtDiaryScreenState.Loading

        viewModelScope.launch {
            getExerciseDetailResultUseCase(idResult).collectRequest(_screenState)
        }
    }

  /*  fun editAutoThought(newText: String) {
        viewModelScope.launch {
            _screenState.value =
                if (editAutoThoughtUseCase(id, newText))
                    ThoughtDiaryScreenState.EditingSuccess
                else
                    ThoughtDiaryScreenState.Error
        }
    }

    fun editAlternativeThought(newText: String) {
        viewModelScope.launch {
            _screenState.value =
                if (editAlternativeThoughtUseCase(id, newText))
                    ThoughtDiaryScreenState.EditingSuccess
                else
                    ThoughtDiaryScreenState.Error
        }
    } */

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("id") id: String,
//            @Assisted("clientId") clientId: String
        ): ThoughtDiaryViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            id: String,
   //         clientId: String
        ) = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(id) as T
        }

        const val OWN = "own"
    }

}
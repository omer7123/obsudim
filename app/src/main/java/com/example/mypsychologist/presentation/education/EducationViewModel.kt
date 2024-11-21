package com.example.mypsychologist.presentation.education

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.example.mypsychologist.domain.useCase.SaveProgressUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.educationUseCases.GetMaterialOfTopicUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases.MarkAsCompleteExerciseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EducationViewModel(
    private val getEducationMaterialUseCase: GetMaterialOfTopicUseCase,
    private val saveProgressUseCase: SaveProgressUseCase,
    private val markAsCompleteExerciseUseCase: MarkAsCompleteExerciseUseCase
) : ViewModel() {

    private val _screenState: MutableLiveData<EducationScreenState> = MutableLiveData()
    val screenState: LiveData<EducationScreenState> = _screenState

    fun getMaterial(id: String){
        _screenState.value = EducationScreenState.Loading
        viewModelScope.launch {
            when (val result = getEducationMaterialUseCase(id)) {
                is Resource.Error -> _screenState.value = EducationScreenState.Error(result.msg.toString())
                Resource.Loading -> _screenState.value = EducationScreenState.Loading
                is Resource.Success -> _screenState.value = EducationScreenState.Content(result.data)
            }
        }
    }

    fun saveProgress(materialId: String) {

        viewModelScope.launch(Dispatchers.IO) {

            when(val res = saveProgressUseCase(EducationMaterialForSaveProgressEntity(materialId))){
                is Resource.Error -> {}
                Resource.Loading -> {}
                is Resource.Success -> {}
            }
        }
    }

    class Factory @Inject constructor(
        private val getEducationMaterialUseCase: GetMaterialOfTopicUseCase,
        private val saveProgressUseCase: SaveProgressUseCase,
        private val markAsCompleteExerciseUseCase: MarkAsCompleteExerciseUseCase
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EducationViewModel(
                getEducationMaterialUseCase, saveProgressUseCase, markAsCompleteExerciseUseCase
            ) as T
        }
    }
}
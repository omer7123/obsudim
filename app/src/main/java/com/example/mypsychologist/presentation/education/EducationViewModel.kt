package com.example.mypsychologist.presentation.education

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.example.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.example.mypsychologist.domain.useCase.SaveProgressUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.educationUseCases.GetMaterialOfTopicUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.exerciseUseCases.MarkAsCompleteExerciseUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class EducationViewModel(
    private val getEducationMaterialUseCase: GetMaterialOfTopicUseCase,
    private val saveProgressUseCase: SaveProgressUseCase,
    private val markAsCompleteExerciseUseCase: MarkAsCompleteExerciseUseCase
) : ViewModel() {

    private val _screenState: MutableLiveData<EducationScreenState> = MutableLiveData()
    val screenState: LiveData<EducationScreenState> = _screenState

    private val _markAsCompleteStatus: MutableLiveData<MarsAsCompleteStatus> = MutableLiveData()
    val marsAsCompleteStatus: LiveData<MarsAsCompleteStatus> = _markAsCompleteStatus

    fun getMaterial(id: String){
        _screenState.value = EducationScreenState.Loading
        viewModelScope.launch {
            getEducationMaterialUseCase(id).collect{result->
                when (result) {
                    is Resource.Error -> _screenState.value = EducationScreenState.Error(result.msg.toString())
                    Resource.Loading -> _screenState.value = EducationScreenState.Loading
                    is Resource.Success -> _screenState.value = EducationScreenState.Content(result.data)
                }
            }

        }
    }

    fun saveProgress(materialId: String) {
        viewModelScope.launch {
            _screenState.value = EducationScreenState.Loading
            when(val res = saveProgressUseCase(EducationMaterialForSaveProgressEntity(materialId))){
                is Resource.Error -> {}
                Resource.Loading -> {}
                is Resource.Success -> {}
            }
        }
    }

    fun markAsCompleteTask(taskId: String){
        if (taskId.isNotEmpty()) {
            viewModelScope.launch {
                markAsCompleteExerciseUseCase(DailyTaskMarkIdEntity(id = taskId)).collect { resource ->
                    when (resource) {
                        is Resource.Error -> _markAsCompleteStatus.value =
                            MarsAsCompleteStatus.Error(resource.msg.toString())

                        Resource.Loading -> _screenState.value =
                            EducationScreenState.Loading

                        is Resource.Success -> _markAsCompleteStatus.value =
                            MarsAsCompleteStatus.Success
                    }
                }
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
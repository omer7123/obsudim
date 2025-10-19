package com.obsudim.mypsychologist.presentation.education.educationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.obsudim.mypsychologist.domain.entity.exerciseEntity.DailyTaskMarkIdEntity
import com.obsudim.mypsychologist.domain.useCase.educationUseCases.GetMaterialOfTopicUseCase
import com.obsudim.mypsychologist.domain.useCase.educationUseCases.SaveProgressUseCase
import com.obsudim.mypsychologist.domain.useCase.exerciseUseCases.MarkAsCompleteExerciseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EducationViewModel(
    private val getEducationMaterialUseCase: GetMaterialOfTopicUseCase,
    private val saveProgressUseCase: SaveProgressUseCase,
    private val markAsCompleteExerciseUseCase: MarkAsCompleteExerciseUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<EducationScreenState> = MutableStateFlow(
        EducationScreenState.Initial
    )
    val screenState: StateFlow<EducationScreenState> = _screenState

    private val _markAsCompleteStatus: MutableLiveData<MarsAsCompleteStatus> = MutableLiveData()
    val marsAsCompleteStatus: LiveData<MarsAsCompleteStatus> = _markAsCompleteStatus

    fun getMaterial(id: String){
        _screenState.value = EducationScreenState.Loading
        viewModelScope.launch {
            getEducationMaterialUseCase(id).collect{ result->
                when (result) {
                    is Resource.Error -> _screenState.value =
                        EducationScreenState.Error(result.msg.toString())
                    Resource.Loading -> _screenState.value = EducationScreenState.Loading
                    is Resource.Success -> {
                        _screenState.value = EducationScreenState.Content(result.data)
                    }
                }
            }
        }
    }

    fun saveProgress(materialId: String) {
        viewModelScope.launch {
            _screenState.value = EducationScreenState.Loading
            when(val res = saveProgressUseCase(EducationMaterialForSaveProgressEntity(materialId))){
                is Resource.Error -> _screenState.value =
                    EducationScreenState.Error(res.msg.toString())
                Resource.Loading -> Unit
                is Resource.Success -> Unit
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
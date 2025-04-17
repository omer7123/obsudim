package com.example.mypsychologist.presentation.psychologist.psychologistWithTaskFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.TaskEntity
import com.example.mypsychologist.domain.useCase.exerciseUseCases.MarkTaskUseCase
import com.example.mypsychologist.domain.useCase.psychologistsUseCases.GetOwnPsychologistsUseCase
import com.example.mypsychologist.domain.useCase.psychologistsUseCases.GetStatusRequestToManagerUseCase
import com.example.mypsychologist.domain.useCase.psychologistsUseCases.GetTasksUseCase
import com.example.mypsychologist.domain.useCase.psychologistsUseCases.GetYourPsychologistsUseCase
import com.example.mypsychologist.presentation.core.ListScreenState
import com.example.mypsychologist.ui.core.delegateItems.DelegateItem
import com.example.mypsychologist.ui.psychologist.psychologistWithTasksFragment.OwnPsychologistDelegateItem
import com.example.mypsychologist.ui.psychologist.psychologistWithTasksFragment.TaskFromPsychologistDelegateItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PsychologistsWithTasksViewModel(
    private val getOwnPsychologistsUseCase: GetOwnPsychologistsUseCase,
    private val getYourPsychologistsUseCase: GetYourPsychologistsUseCase,
    private val getStatusRequestToManagerUseCase: GetStatusRequestToManagerUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val markTaskUseCase: MarkTaskUseCase
) : ViewModel() {
    private val _screenState: MutableStateFlow<PsychologistWithTaskScreenState> =
        MutableStateFlow(PsychologistWithTaskScreenState.Init)
    val screenState: StateFlow<PsychologistWithTaskScreenState>
        get() = _screenState.asStateFlow()


    fun initial() {
        _screenState.value = PsychologistWithTaskScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getOwnPsychologists()
        }
    }

    private suspend fun getOwnPsychologists() {
        when (val result = getYourPsychologistsUseCase()) {
            is Resource.Error -> _screenState.value =
                PsychologistWithTaskScreenState.Error(result.msg.toString())

            Resource.Loading -> _screenState.value = PsychologistWithTaskScreenState.Loading
            is Resource.Success -> {
                if (result.data.isEmpty()) _screenState.value =
                    PsychologistWithTaskScreenState.PlaceHolderPsychologistsState
                else getTasks()
            }
        }
    }

    private suspend fun getTasks() {
        when (val result = getTasksUseCase()) {
            is Resource.Error -> {
                _screenState.value = PsychologistWithTaskScreenState.Error(result.msg.toString())
            }

            Resource.Loading -> ListScreenState.Loading
            is Resource.Success -> {
                if (result.data.isEmpty())
                    _screenState.value =
                        PsychologistWithTaskScreenState.PlaceHolderTaskState
                else _screenState.value =
                    PsychologistWithTaskScreenState.Content(convertTaskListToDelegateItems(result.data))
            }
        }
    }

    fun getPsychologists() {
        viewModelScope.launch(Dispatchers.IO) {

            when (val res = getOwnPsychologistsUseCase()) {
                is Resource.Error -> {
                    Log.e("Erroe", res.msg.toString())
                    _screenState.value = PsychologistWithTaskScreenState.Error(res.msg.toString())
                }

                Resource.Loading -> {}
                is Resource.Success -> {
                    _screenState.value =
                        PsychologistWithTaskScreenState.Content(convertListToDelegateItems(res.data))
                }
            }
        }
    }

    private fun convertListToDelegateItems(managers: List<ManagerEntity>): List<DelegateItem> {
        return managers.map { manager ->
            OwnPsychologistDelegateItem(manager)
        }
    }

    private fun convertTaskListToDelegateItems(tasks: List<TaskEntity>): List<DelegateItem> {
        return tasks.map { task ->
            TaskFromPsychologistDelegateItem(task)
        }
    }

    fun markTask(taskId: String, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            markTaskUseCase(taskId, isChecked)
        }
    }

    class Factory @Inject constructor(
        private val getOwnPsychologistsUseCase: GetOwnPsychologistsUseCase,
        private val getYourPsychologistsUseCase: GetYourPsychologistsUseCase,
        private val getStatusRequestToManagerUseCase: GetStatusRequestToManagerUseCase,
        private val getTasksUseCase: GetTasksUseCase,
        private val markTaskUseCase: MarkTaskUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PsychologistsWithTasksViewModel(
                getOwnPsychologistsUseCase,
                getYourPsychologistsUseCase,
                getStatusRequestToManagerUseCase,
                getTasksUseCase,
                markTaskUseCase
            ) as T
        }
    }
}
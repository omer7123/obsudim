package com.example.mypsychologist.presentation.psychologist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.TaskEntity
import com.example.mypsychologist.domain.useCase.MarkTaskUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.psychologistsUseCases.GetOwnPsychologistsUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.psychologistsUseCases.GetStatusRequestToManagerUseCase
import com.example.mypsychologist.domain.useCase.retrofitUseCase.psychologistsUseCases.GetTasksUseCase
import com.example.mypsychologist.presentation.ListScreenState
import com.example.mypsychologist.ui.DelegateItem
import com.example.mypsychologist.ui.psychologist.OwnPsychologistDelegateItem
import com.example.mypsychologist.ui.psychologist.TaskFromPsychologistDelegateItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PsychologistsWithTasksViewModel(
    private val getOwnPsychologistsUseCase: GetOwnPsychologistsUseCase,
    private val getStatusRequestToManagerUseCase: GetStatusRequestToManagerUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val markTaskUseCase: MarkTaskUseCase
) : ViewModel() {
    private val _screenState: MutableStateFlow<ListScreenState> =
        MutableStateFlow(ListScreenState.Init)
    val screenState: StateFlow<ListScreenState>
        get() = _screenState.asStateFlow()


    fun initial() {
        _screenState.value = ListScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            /*        val status = getStatusRequestToManagerUseCase()
                    Log.e("Status: ", status.toString())
                    when (status) {
                        true -> */
            getTasks()

            //    false -> getPsychologists() }
        }
    }

    private suspend fun getTasks() {
        when (val result = getTasksUseCase()) {
            is Resource.Error -> _screenState.value = ListScreenState.Error
            Resource.Loading -> ListScreenState.Loading
            is Resource.Success -> {
                Log.e("TASK", result.data.toString())

                _screenState.value =
                    ListScreenState.Data(convertTaskListToDelegateItems(result.data))
            }
        }
    }

    fun getPsychologists() {
        viewModelScope.launch(Dispatchers.IO) {

            when (val res = getOwnPsychologistsUseCase()) {
                is Resource.Error -> _screenState.value = ListScreenState.Error
                Resource.Loading -> {}
                is Resource.Success -> {
                    _screenState.value =
                        ListScreenState.Data(convertListToDelegateItems(res.data))
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

    private suspend fun List<OwnPsychologistDelegateItem>.concatenate(): List<DelegateItem> {
        val psychologistsWithTasks = mutableListOf<DelegateItem>()

        forEach { psychologist ->

            psychologistsWithTasks.add(psychologist)
//            psychologistsWithTasks.addAll(getTasksUseCase(psychologist.content().first).toDelegateItem())
        }

        return psychologistsWithTasks
    }

    fun markTask(taskId: String, isChecked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            markTaskUseCase(taskId, isChecked)
        }
    }

    class Factory @Inject constructor(
        private val getOwnPsychologistsUseCase: GetOwnPsychologistsUseCase,
        private val getStatusRequestToManagerUseCase: GetStatusRequestToManagerUseCase,
        private val getTasksUseCase: GetTasksUseCase,
        private val markTaskUseCase: MarkTaskUseCase
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PsychologistsWithTasksViewModel(
                getOwnPsychologistsUseCase,
                getStatusRequestToManagerUseCase,
                getTasksUseCase,
                markTaskUseCase
            ) as T
        }
    }
}
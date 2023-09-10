package com.example.mypsychologist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.domain.entity.TaskEntity
import com.example.mypsychologist.domain.useCase.AddTaskUseCase
import com.example.mypsychologist.domain.useCase.DeleteTaskUseCase
import com.example.mypsychologist.domain.useCase.GetClientTasksUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClientTasksViewModel @AssistedInject constructor(
    @Assisted private val clientId: String,
    private val getClientTasksUseCase: GetClientTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _screenState: MutableStateFlow<TasksScreenState> =
        MutableStateFlow(TasksScreenState.Init)
    val screenState: StateFlow<TasksScreenState>
        get() = _screenState.asStateFlow()

    init {
        _screenState.value = TasksScreenState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value =
                try {
                    TasksScreenState.Data(getClientTasksUseCase(clientId))
                } catch (t: Throwable) {
                    TasksScreenState.Error
                }
        }
    }

    fun addTask(task: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val taskId = addTaskUseCase(task, clientId)

                val newMap = (screenState.value as TasksScreenState.Data).items.toMutableMap()
                newMap[taskId] = TaskEntity(text = task)

                _screenState.value = TasksScreenState.Data(HashMap(newMap))
            }
        } catch (t: Throwable) {
            _screenState.value = TasksScreenState.Error
        }
    }

    fun deleteTask(taskId: String) {
            try {
                viewModelScope.launch(Dispatchers.IO) {

                    Log.d("aaa", taskId)
                    deleteTaskUseCase(taskId, clientId)

                    _screenState.value = TasksScreenState.Data(
                        HashMap((screenState.value as TasksScreenState.Data).items.filter {
                            it.key != taskId
                        })
                    )
                }
            } catch (t: Throwable) {
                _screenState.value = TasksScreenState.Error
            }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted clientId: String
        ): ClientTasksViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: Factory, clientId: String) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    assistedFactory.create(clientId) as T
            }
    }
}
package com.example.mypsychologist.presentation.psychologist

import com.example.mypsychologist.domain.entity.TaskEntity

sealed interface TasksScreenState {
    data object Init: TasksScreenState
    data object Error: TasksScreenState
    data object Loading: TasksScreenState
    class Data(val items: HashMap<String, TaskEntity>): TasksScreenState
}
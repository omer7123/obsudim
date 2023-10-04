package com.example.mypsychologist.presentation.psychologist

import com.example.mypsychologist.domain.entity.TaskEntity


sealed interface TasksScreenState {
    object Init: TasksScreenState
    object Error: TasksScreenState
    object Loading: TasksScreenState
    class Data(val items: HashMap<String, TaskEntity>): TasksScreenState
}
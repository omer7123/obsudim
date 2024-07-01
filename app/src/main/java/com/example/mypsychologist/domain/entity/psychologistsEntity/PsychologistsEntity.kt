package com.example.mypsychologist.domain.entity.psychologistsEntity

data class ManagerEntity(
    val username: String,
    val description: String = "",
    val city: String = "",
    val company: String,
    val gender: String = "",
    val birthDate: String = ""
)

data class TaskEntity(
    val text: String,
    val userId: String,
    val testTitle: String,
    val testId: String,
    val isCompleted: Boolean
)

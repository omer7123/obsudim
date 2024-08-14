package com.example.mypsychologist.domain.entity.psychologistsEntity

data class ManagerEntity(
    val id: String,
    val gender: String,
    val username: String,
    val birthDate: String,
    val email: String,
    val description: String,
    val city: String,
    val isActive: Boolean,
    val company: String,
    val online: Boolean,
    val faceToFace: Boolean,
)

data class TaskEntity(
    val id: String,
    val text: String,
    val clientId: String,
    val psychologistId: String,
    val testTitle: String,
    val testId: String,
    val isCompleted: Boolean,
    val testDescription: String,
)

data class SendRequestToPsychologistEntity(
    val id: String,
    val text: String,
)

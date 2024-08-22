package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagerModel(
    val id: String,
    val gender: String,
    val username: String,
    @SerialName("birth_date")
    val birthDate: String,
    val email: String,
    val description: String,
    @SerialName("role_id")
    val roleId: Int,
    val city: String,
    @SerialName("is_active")
    val isActive: Boolean,
    val company: String,
    val online: Boolean,
    @SerialName("face_to_face")
    val faceToFace: Boolean,
)

@Serializable
data class TaskModel(
    val id: String,
    val text: String,
    @SerialName("test_title")
    val testTitle: String,
    @SerialName("psychologist_id")
    val psychologistId: String,
    @SerialName("test_id")
    val testId: String,
    @SerialName("is_complete")
    val isCompleted: Boolean,
    @SerialName("test_description")
    val testDescription: String,
)

@Serializable
data class SendRequestToPsychologistModel(
    @SerialName("user_id")
    val id: String,
    val text: String,
)

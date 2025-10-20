package com.obsudim.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoModel(
    @SerialName("birth_date")
    val birthDate: String,
    val gender: String,
    val username: String,
    val request: List<Int>,
    val city: String,
    val description: String,
    val type: Int
)

@Serializable
data class UserDataModel(
    val id: String,
    val username: String,
    val email: String,
    val city: String,
    val company: String?,
    val online: String?,
    val gender: String,
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("phone_number")
    val phoneNumber: String,
    val description: String?,
    @SerialName("is_active")
    val isActive: String?,
    val department: String?,
    @SerialName("face_to_face")
    val faceToFace: Boolean?,
    @SerialName("role_id")
    val roleId: Int,
)
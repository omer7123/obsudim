package com.obsudim.mypsychologist.domain.entity.priofileEntity

data class UserDataEntity(
    val id: String,
    val username: String,
    val email: String,
    val city: String,
    val company: String?,
    val online: Boolean?,
    val gender: String,
    val birthDate: String,
    val phoneNumber: String,
    val description: String?,
    val isActive: Boolean?,
    val department: String?,
    val faceToFace: Boolean?,
    val roleId: Int,
)

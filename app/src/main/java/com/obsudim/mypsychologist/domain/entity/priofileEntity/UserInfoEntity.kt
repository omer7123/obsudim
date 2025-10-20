package com.obsudim.mypsychologist.domain.entity.priofileEntity

data class UserInfoEntity(
    val birthDate: String,
    val gender: String,
    val username: String,
    val request: List<Int>,
    val city: String,
    val description: String,
    val type: Int
)

data class UserDataEntity(
    val id: String,
    val username: String,
    val email: String,
    val city: String,
    val company: String?,
    val online: String?,
    val gender: String,
    val birthDate: String,
    val phoneNumber: String,
    val description: String?,
    val isActive: String?,
    val department: String?,
    val faceToFace: Boolean?,
    val roleId: Int,
)
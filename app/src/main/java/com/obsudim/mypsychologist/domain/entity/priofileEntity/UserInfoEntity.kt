package com.obsudim.mypsychologist.domain.entity.priofileEntity


data class UserInfoEntity(
    val name: String = "",
    val description: String = "",
    val birthday: String = "",
    val gender: String = "",
    val company: String = "",
    val city: String = "",
    val phone: String = ""
)

fun UserInfoEntity.getMapOfMembers() =
    mapOf(
        ::name.name to name,
        ::gender.name to gender,
        ::city.name to city,
        ::description.name to description,
        ::birthday.name to birthday,
        ::company.name to company,
        ::phone.name to phone
    )

data class UserDataEntity(
    val id: String,
    val username: String,
    val email: String,
    val city: String,
    val company: String?,
    val online: Boolean,
    val gender: String,
    val birthDate: String,
    val phoneNumber: String,
    val description: String?,
    val isActive: Boolean,
    val department: String?,
    val faceToFace: Boolean?,
    val roleId: Int,
)
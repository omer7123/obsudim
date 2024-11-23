package com.example.mypsychologist.domain.entity

data class ClientInfoEntity(
    val name: String = "",
    val birthday: String = "",
    val gender: String = "",
    val request: List<TagEntity> = listOf(),
    val city: String = "Томск"
)

fun ClientInfoEntity.getMapOfMembers() =
    mapOf(
        ::name.name to name,
        ::gender.name to gender,
        ::city.name to city
    )

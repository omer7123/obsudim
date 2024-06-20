package com.example.mypsychologist.domain.entity

data class ClientInfoEntity(
        val name: String = "",
        val birthday: String = "",
        val gender: String = "",
        val request: List<RequestEntity> = listOf(),
        val city: String = ""
)

fun ClientInfoEntity.getMapOfMembers() =
        mapOf(
                ::name.name to name,
                ::birthday.name to birthday,
                ::gender.name to gender,
                ::city.name to city,
        )

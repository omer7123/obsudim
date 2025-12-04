package com.obsudim.mypsychologist.domain.entity

data class ClientInfoEntity(
    val name: String = "",
    val description: String = "",
    val birthday: String = "",
    val gender: String = "",
    val company: String = "",
    val city: String = "",
    val phone: String = ""
)

fun ClientInfoEntity.getMapOfMembers() =
    mapOf(
        ::name.name to name,
        ::gender.name to gender,
        ::city.name to city
    )

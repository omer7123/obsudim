package com.example.mypsychologist.domain.entity

data class PsychologistInfo(
    val name: String = "",
    val education: String = "",
    val about: String = "",
    val city: String = "",
    val formats: String = "",
    val specialization: List<String> = listOf(),
    val courses: List<String> = listOf()
)

fun PsychologistInfo.mapOfValidationMembers() =
    mapOf(
        ::name.name to name,
        ::education.name to education,
        ::about.name to about,
        ::city.name to city,
        ::formats.name to formats
    )
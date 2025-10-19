package com.obsudim.mypsychologist.domain.entity

data class ProblemEntity (
    val title: String = "",
    val moods: List<String> = listOf(),
    val completed: Boolean = false,
    val actual: Boolean = false,
    val goal: String,
    val id: String = ""
)




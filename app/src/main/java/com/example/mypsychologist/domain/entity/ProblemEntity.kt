package com.example.mypsychologist.domain.entity

import com.google.firebase.database.Exclude


data class ProblemEntity (
    val title: String = "",
    val moods: List<String> = listOf(),
    val completed: Boolean = false,
    @Exclude
    val actual: Boolean = false
)




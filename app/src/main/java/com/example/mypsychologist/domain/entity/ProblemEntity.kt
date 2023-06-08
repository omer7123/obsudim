package com.example.mypsychologist.domain.entity

data class ProblemEntity (
    val title: String,
    val completed: Boolean = false,
    val actual: Boolean = false
)

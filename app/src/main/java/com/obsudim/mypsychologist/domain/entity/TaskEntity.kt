package com.obsudim.mypsychologist.domain.entity

data class TaskEntity(
    val text: String = "",
    val completed: Boolean = false,
    val userId: String = ""
)

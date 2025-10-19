package com.obsudim.mypsychologist.domain.entity

data class TestResultEntity(
    val score: Int = 0,
    val conclusion: String = "",
    val timestamp: Long = 0L
)

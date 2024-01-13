package com.example.mypsychologist.domain.entity

data class TestScalesResultEntity(
    val scalesWithScores: HashMap<String, Int> = hashMapOf(),
    val timestamp: Long = 0L
)
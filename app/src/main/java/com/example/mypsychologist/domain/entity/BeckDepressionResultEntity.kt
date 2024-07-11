package com.example.mypsychologist.domain.entity

data class BeckDepressionResultEntity(
    val depressionScore: Pair<Int, Int>,
    val cognitiveDepression: Pair<Int, Int>,
    val somaticDepression: Pair<Int, Int>
)
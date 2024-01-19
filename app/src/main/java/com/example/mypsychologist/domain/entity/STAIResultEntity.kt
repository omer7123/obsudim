package com.example.mypsychologist.domain.entity

data class STAIResultEntity(
    val situationalAnxiety: Pair<Int, Int> = Pair(0, 0),
    val personalAnxiety: Pair<Int, Int> = Pair(0, 0)
)

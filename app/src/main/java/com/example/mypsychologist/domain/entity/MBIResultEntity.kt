package com.example.mypsychologist.domain.entity

data class MBIResultEntity (
    val emotionalExhaustion: Pair<Int, Int> = Pair(0,0),
    val depersonalization: Pair<Int, Int> = Pair(0,0),
    val reductionOfPersonalAchievements: Pair<Int, Int> = Pair(0,0),
)

package com.example.mypsychologist.domain.entity

data class JASResultEntity (
    val apatheticThoughts: Pair<Int, Int> = Pair(0,0),
    val apatheticActions: Pair<Int, Int> = Pair(0,0)
)



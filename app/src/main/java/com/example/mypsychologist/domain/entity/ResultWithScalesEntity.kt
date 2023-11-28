package com.example.mypsychologist.domain.entity

data class ResultWithScalesEntity(
    val score: Int,
    val conclusion: Int,
    val scales: HashMap<Int, Int>
)
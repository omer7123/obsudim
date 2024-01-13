package com.example.mypsychologist.domain.entity


data class DASSResultEntity (
    val stressScoreAndConclusion: Pair<Int, Int> = Pair(0, 0),
    val anxietyScoreAndConclusion: Pair<Int, Int> = Pair(0, 0),
    val depressionScoreAndConclusion: Pair<Int, Int> = Pair(0, 0)
)
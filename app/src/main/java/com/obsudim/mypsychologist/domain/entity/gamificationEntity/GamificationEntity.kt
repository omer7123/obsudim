package com.obsudim.mypsychologist.domain.entity.gamificationEntity

data class CurrentScoreEntity(
    val score: Int
)

data class WeeklyScoresEntity(
    val scores: List<ScoresEntity>
)

data class ScoresEntity(
    val date: String,
    val score: Int
)
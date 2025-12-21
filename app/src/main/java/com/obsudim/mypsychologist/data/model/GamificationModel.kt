package com.obsudim.mypsychologist.data.model

import kotlinx.serialization.Serializable


@Serializable
data class CurrentScoreModel(
    val score: Int
)

@Serializable
data class WeeklyScoresModel(
    val scores: List<ScoresModel>
)

@Serializable
data class ScoresModel(
    val date: String,
    val score: Int
)
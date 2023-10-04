package com.example.mypsychologist.domain.entity

data class RebtProblemProgressEntity(
    val problem: String,
    val problemAnalysisCompleted: Boolean,
    val beliefsCheckCompleted: Boolean,
    val beliefsAnalysisCompleted: Boolean,
    val dialogCompleted: Boolean
)

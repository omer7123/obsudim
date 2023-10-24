package com.example.mypsychologist.domain.entity

data class RebtProblemProgressEntity(
    val problem: String = "",
    val problemAnalysisCompleted: Boolean = false,
    val beliefsCheckCompleted: Boolean = false,
    val beliefsAnalysisCompleted: Boolean = false,
    val dialogCompleted: Boolean = false
)

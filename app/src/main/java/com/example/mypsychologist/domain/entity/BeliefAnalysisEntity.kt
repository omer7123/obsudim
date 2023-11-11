package com.example.mypsychologist.domain.entity

data class BeliefAnalysisEntity(
    val feelingsAndActions: String = "",
    val motivation: String = "",
    val interferences: String = "",
    val dependent: String = "",
    val results: String = ""
)

fun BeliefAnalysisEntity.getMapOfMembers() =
    mapOf(
        ::feelingsAndActions.name to feelingsAndActions,
        ::motivation.name to motivation,
        ::interferences.name to interferences,
        ::dependent.name to dependent,
        ::results.name to results
    )

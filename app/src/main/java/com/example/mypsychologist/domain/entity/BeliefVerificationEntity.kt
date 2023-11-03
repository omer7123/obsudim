package com.example.mypsychologist.domain.entity

data class BeliefVerificationEntity(
    val truthfulness: String = "",
    val consistency: String = "",
    val benefit: String = ""
)

fun BeliefVerificationEntity.getMapOfMembers() =
    mapOf(
        ::truthfulness.name to truthfulness,
        ::consistency.name to consistency,
        ::benefit.name to benefit
    )
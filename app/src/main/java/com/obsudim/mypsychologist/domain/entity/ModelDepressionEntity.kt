package com.obsudim.mypsychologist.domain.entity


data class ModelDepressionPostReqEntity(
    val text: String
)

data class ModelDepressionRespEntity(
    val prediction: Int,
    val probabilityNegative: Double,
    val probabilityPositive: Double,
    val text: String,
)
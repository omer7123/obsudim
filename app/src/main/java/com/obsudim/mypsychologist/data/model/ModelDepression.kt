package com.obsudim.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModelDepressionPostReq(
    val text: String
)

@Serializable
data class ModelDepressionResp(
    val prediction: Int,
    @SerialName("probability_negative")
    val probabilityNegative: Double,
    @SerialName("probability_positive")
    val probabilityPositive: Double,
    val text: String,
)
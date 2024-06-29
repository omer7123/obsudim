package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeliefCheckModel(
    val truthfulness: String,
    val consistency: String,
    val benefit: String,
    @SerialName("intermediate_conviction_id")
    val beliefId: String = ""
)

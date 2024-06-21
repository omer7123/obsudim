package com.example.mypsychologist.data.model

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class BeliefCheckModel(
    val truthfulness: String,
    val consistency: String,
    val benefit: String,
    @Json(name = "intermediate_conviction_id")
    val beliefId: String
)

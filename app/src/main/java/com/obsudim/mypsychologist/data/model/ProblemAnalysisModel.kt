package com.obsudim.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProblemAnalysisModel(
    @SerialName("problem_id")
    val problemId: String = "",
    val disadaptive: String = "",
    val adaptive: String = ""
)

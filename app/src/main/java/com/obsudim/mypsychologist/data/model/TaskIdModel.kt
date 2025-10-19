package com.obsudim.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskIdModel(
    @SerialName("task_id")
    val id: String
)

package com.example.mypsychologist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CBTDiaryModel (
    val situation: String,
    val mood: String,
    val level: Int,
    @SerialName("auto_thought")
    val autoThought: String,
    val proofs: String,
    val refutations: String,
    val alternativeThought: String,
    @SerialName("new_mood")
    val newMood: String,
    @SerialName("new_level")
    val newLevel: Int,
    val behavioral: String
)

@Serializable
data class CBTInputDiaryModel (
    val situation: String,
    val mood: String,
    val level: Int,
    @SerialName("auto_thought")
    val autoThought: String,
    val proofs: String,
    val refutations: String,
    @SerialName("alternative_thought")
    val alternativeThought: String,
    @SerialName("new_mood")
    val newMood: String,
    @SerialName("new_level")
    val newLevel: Int,
    val behavioral: String
)

@Serializable
data class CBTDiaryCardModel(
    val id: String,
    val situation: String
)
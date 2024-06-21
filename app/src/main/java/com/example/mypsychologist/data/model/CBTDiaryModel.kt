package com.example.mypsychologist.data.model

data class CBTDiaryModel (
    val situation: String,
    val mood: String,
    val level: Int,
    val autoThought: String,
    val proofs: String,
    val refutations: String,
    val alternativeThought: String,
    val newMood: String,
    val newLevel: Int
)
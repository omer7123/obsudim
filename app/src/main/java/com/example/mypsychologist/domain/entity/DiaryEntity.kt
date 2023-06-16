package com.example.mypsychologist.domain.entity

data class DiaryEntity(
    val situation: String,
    val mood: String,
    val autoThought: String,
    val proofs: String,
    val refutations: String,
    val alternativeThought: String,
    val newMood: String,
)

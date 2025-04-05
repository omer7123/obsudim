@file:JvmName("InputItemEntityKt")

package com.example.mypsychologist.domain.entity


data class ThoughtDiaryEntity(
    val situation: String = "",
    val mood: String = "",
    val level: Int = 50,
    val autoThought: String = "",
    val proofs: String = "",
    val refutations: String = "",
    val alternativeThought: String = "",
    val newMood: String = "",
    val newLevel: Int = 50,
    val behaviour: String = ""
)

fun ThoughtDiaryEntity.getMapOfMembers() =
    mapOf(
        ::situation.name to situation,
        ::mood.name to mood,
        ::level.name to level.toString(),
        ::autoThought.name to autoThought,
        ::proofs.name to proofs,
        ::refutations.name to refutations,
        ::alternativeThought.name to alternativeThought,
        ::newMood.name to newMood,
        ::newLevel.name to newLevel.toString(),

    )




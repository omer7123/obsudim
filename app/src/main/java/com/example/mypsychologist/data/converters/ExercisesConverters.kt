package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.BeliefCheckModel
import com.example.mypsychologist.data.model.CBTDiaryModel
import com.example.mypsychologist.data.model.ProblemModel
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity

fun BeliefCheckModel.toEntity() =
    BeliefVerificationEntity(truthfulness, consistency, benefit, beliefId)

fun BeliefVerificationEntity.toModel() =
    BeliefCheckModel(truthfulness, consistency, benefit, beliefId)

fun CBTDiaryModel.toEntity() =
    ThoughtDiaryEntity(
        situation,
        mood,
        level,
        autoThought,
        proofs,
        refutations,
        "alternativeThought",
        newMood,
        newLevel
    )

fun ThoughtDiaryEntity.toModel() =
    CBTDiaryModel(
        "string",
        situation,
        mood,
        level,
        autoThought,
        proofs,
        refutations,
     //   alternativeThought,
        newMood,
        newLevel,
        behaviour
    )

fun ProblemModel.toEntity() =
    ProblemEntity(title = description, goal = goal)

fun List<ProblemModel>.toEntities()=
    this.map { it.toEntity() }

fun ProblemEntity.toModel() =
    ProblemModel(title, goal)

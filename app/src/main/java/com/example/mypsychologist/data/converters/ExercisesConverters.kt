package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.BeliefCheckModel
import com.example.mypsychologist.data.model.CBTDiaryCardModel
import com.example.mypsychologist.data.model.CBTDiaryModel
import com.example.mypsychologist.data.model.GetProblemModel
import com.example.mypsychologist.data.model.SaveProblemModel
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.entity.DiaryRecordEntity
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
        situation,
        mood,
        level,
        autoThought,
        proofs,
        refutations,
        alternativeThought,
        newMood,
        newLevel,
        behaviour
    )

fun CBTDiaryCardModel.toEntity() =
    DiaryRecordEntity(id, situation)

fun List<CBTDiaryCardModel>.toDiaryEntities() =
    map { it.toEntity() }

fun GetProblemModel.toEntity() =
    ProblemEntity(title = description, goal = goal, id = userId)

fun List<GetProblemModel>.toEntities()=
    this.map { it.toEntity() }

fun ProblemEntity.toModel() =
    SaveProblemModel(title, goal)

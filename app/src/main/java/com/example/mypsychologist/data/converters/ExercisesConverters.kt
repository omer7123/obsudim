package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.CBTDiaryCardModel
import com.example.mypsychologist.data.model.CBTDiaryModel
import com.example.mypsychologist.data.model.GetProblemModel
import com.example.mypsychologist.data.model.ProblemAnalysisModel
import com.example.mypsychologist.data.model.SaveProblemModel
import com.example.mypsychologist.domain.entity.DiaryRecordEntity
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.entity.getMapOfFilledMembers
import com.example.mypsychologist.extensions.containsKeys


fun CBTDiaryModel.toEntity() =
    ThoughtDiaryEntity(
        situation,
        mood,
        level,
        autoThought,
        proofs,
        refutations,
        alternativeThought,
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

fun List<GetProblemModel>.toEntities() =
    this.map { it.toEntity() }

fun ProblemEntity.toModel() =
    SaveProblemModel(title, goal)

fun ProblemAnalysisEntity.toModels(): List<ProblemAnalysisModel> = run {
    val models = mutableListOf<ProblemAnalysisModel>()
    val map = getMapOfFilledMembers()

    models.add(
        ProblemAnalysisModel(
            problemId,
            map[::dogmaticRequirement.name]!!,
            map[::flexiblePreference.name]!!
        )
    )

    if (map.containsKeys(::dramatization.name, ::perspective.name))
        models.add(
            ProblemAnalysisModel(
                problemId,
                map[::dramatization.name]!!,
                map[::perspective.name]!!
            )
        )

    if (map.containsKeys(::lft.name, ::hft.name))
        models.add(ProblemAnalysisModel(problemId, map[::lft.name]!!, map[::hft.name]!!))

    if (map.containsKeys(::humiliatingRemarks.name, ::unconditionalAcceptance.name))
        models.add(
            ProblemAnalysisModel(
                problemId,
                map[::humiliatingRemarks.name]!!,
                map[::unconditionalAcceptance.name]!!
            )
        )

    models
}


package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.CBTDiaryCardModel
import com.obsudim.mypsychologist.data.model.CBTDiaryModel
import com.obsudim.mypsychologist.data.model.GetProblemModel
import com.obsudim.mypsychologist.data.model.ProblemAnalysisModel
import com.obsudim.mypsychologist.data.model.SaveProblemModel
import com.obsudim.mypsychologist.domain.entity.DiaryRecordEntity
import com.obsudim.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.obsudim.mypsychologist.domain.entity.ProblemEntity
import com.obsudim.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.obsudim.mypsychologist.domain.entity.getMapOfFilledMembers
import com.obsudim.mypsychologist.extensions.containsKeys


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

fun GetProblemModel.toEntity() =
    ProblemEntity(title = description, goal = goal, id = userId)
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


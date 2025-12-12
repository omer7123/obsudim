package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.CurrentScoreModel
import com.obsudim.mypsychologist.data.model.ScoresModel
import com.obsudim.mypsychologist.data.model.WeeklyScoresModel
import com.obsudim.mypsychologist.domain.entity.priofileEntity.CurrentScoreEntity
import com.obsudim.mypsychologist.domain.entity.priofileEntity.ScoresEntity
import com.obsudim.mypsychologist.domain.entity.priofileEntity.WeeklyScoresEntity


fun CurrentScoreModel.toEntity() =
    CurrentScoreEntity(
        score = score
    )

fun WeeklyScoresModel.toEntity() =
    WeeklyScoresEntity(
        scores = scores.map {it.toEntity() }
    )

fun ScoresModel.toEntity() =
    ScoresEntity(
        date = date,
        score = score
    )
package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.ThemeModel
import com.example.mypsychologist.domain.entity.educationEntity.ThemeEntity

fun ThemeModel.toEntity() =
    ThemeEntity(id, theme, score, maxScore)
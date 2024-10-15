package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.EducationMaterialForSaveProgressModel
import com.example.mypsychologist.data.model.ItemMaterialModel
import com.example.mypsychologist.data.model.ThemeModel
import com.example.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.domain.entity.educationEntity.ThemeEntity

fun ThemeModel.toEntity() =
    ThemeEntity(id, theme, score, maxScore)

fun ItemMaterialModel.toEntity() = ItemMaterialEntity(id, text)

fun EducationMaterialForSaveProgressEntity.toModel() = EducationMaterialForSaveProgressModel(id)
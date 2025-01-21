package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.EducationMaterialForSaveProgressModel
import com.example.mypsychologist.data.model.EducationsModel
import com.example.mypsychologist.data.model.ItemMaterialModel
import com.example.mypsychologist.data.model.ThemeModel
import com.example.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.example.mypsychologist.domain.entity.educationEntity.EducationsEntity
import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.domain.entity.educationEntity.TopicEntity

fun ThemeModel.toEntity(url: String) =
    TopicEntity(id, theme, score, maxScore, url.dropLast(1)+lincToPicture)

fun EducationsModel.toEntity() = EducationsEntity(theme,score,maxScore, materials = materials.map { it.toEntity() })
fun ItemMaterialModel.toEntity() = ItemMaterialEntity(id, text)

fun EducationMaterialForSaveProgressEntity.toModel() = EducationMaterialForSaveProgressModel(id)
package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.EducationMaterialForSaveProgressModel
import com.example.mypsychologist.data.model.EducationsModel
import com.example.mypsychologist.data.model.ItemMaterialModel
import com.example.mypsychologist.data.model.SubtopicModel
import com.example.mypsychologist.data.model.ThemeModel
import com.example.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.example.mypsychologist.domain.entity.educationEntity.EducationsEntity
import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.domain.entity.educationEntity.SubtopicEntity
import com.example.mypsychologist.domain.entity.educationEntity.TopicEntity

fun ThemeModel.toEntity(url: String) =
    TopicEntity(id, theme, score, maxScore, url.dropLast(1)+lincToPicture)

fun EducationsModel.toEntity() = EducationsEntity(
    theme = theme,
    maxScore = maxScore,
    materials = subtopics.map { it.toEntity() }
)

fun SubtopicModel.toEntity() = SubtopicEntity(subtitle, cards.map { it.toEntity() })
fun ItemMaterialModel.toEntity() =
    ItemMaterialEntity(
        id = id,
        text = text,
        linkToPicture = "https://xn--b1afb6bcb.xn--c1ajjlbco7a.xn----gtbbcb4bjf2ak.xn--p1ai$linkToPicture"
    )

fun EducationMaterialForSaveProgressEntity.toModel() = EducationMaterialForSaveProgressModel(id)
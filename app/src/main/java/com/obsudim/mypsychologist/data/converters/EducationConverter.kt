package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.EducationMaterialForSaveProgressModel
import com.obsudim.mypsychologist.data.model.EducationsModel
import com.obsudim.mypsychologist.data.model.ItemMaterialModel
import com.obsudim.mypsychologist.data.model.ThemeModel
import com.obsudim.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.obsudim.mypsychologist.domain.entity.educationEntity.EducationsEntity
import com.obsudim.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.obsudim.mypsychologist.domain.entity.educationEntity.TopicEntity

fun ThemeModel.toEntity(url: String) =
    TopicEntity(id, theme, "${url.dropLast(1)+link}", tags, educationMaterials = educationMaterials.map{it.toEntity()})

fun EducationsModel.toEntity() = EducationsEntity(
    id = id,
    type = type,
    number = number,
    title = title,
    linkToPicture = "https://xn--2-ctbib7ccc.xn--c1ajjlbco7a.xn----gtbbcb4bjf2ak.xn--p1ai$linkToPicture",
    subtitle = subtitle,
    cards = cards.map{it.toEntity()}
)

fun ItemMaterialModel.toEntity() =
    ItemMaterialEntity(
        id = id,
        text = text,
        number = number,
        linkToPicture = "https://xn--2-ctbib7ccc.xn--c1ajjlbco7a.xn----gtbbcb4bjf2ak.xn--p1ai$linkToPicture"
    )

fun EducationMaterialForSaveProgressEntity.toModel() = EducationMaterialForSaveProgressModel(id)
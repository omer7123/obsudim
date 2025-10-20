package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.TagModel
import com.obsudim.mypsychologist.domain.entity.TagEntity

fun TagModel.toEntity() =
    TagEntity(id, text)
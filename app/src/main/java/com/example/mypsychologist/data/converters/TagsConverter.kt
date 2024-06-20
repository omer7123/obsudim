package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.TagModel
import com.example.mypsychologist.domain.entity.TagEntity

fun TagModel.toEntity() =
    TagEntity(id, text)
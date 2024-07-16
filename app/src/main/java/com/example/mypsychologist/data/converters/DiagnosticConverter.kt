package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.TestModel
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity

fun TestModel.toEntity() =
    TestEntity(title, description, testId)
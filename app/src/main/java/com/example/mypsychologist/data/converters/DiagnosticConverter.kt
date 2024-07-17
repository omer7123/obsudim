package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.SaveTestResultModel
import com.example.mypsychologist.data.model.ScaleResultModel
import com.example.mypsychologist.data.model.TestModel
import com.example.mypsychologist.domain.entity.diagnosticEntity.SaveTestResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.ScaleResultEntity
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity

fun TestModel.toEntity() =
    TestEntity(title, description, testId)

fun SaveTestResultEntity.toModel() =
    SaveTestResultModel(date, results.map { it.toModel() }, testId)

private fun ScaleResultEntity.toModel() =
    ScaleResultModel(scaleId, score)
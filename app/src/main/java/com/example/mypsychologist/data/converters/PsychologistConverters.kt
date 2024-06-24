package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.ManagerModel
import com.example.mypsychologist.data.model.TaskModel
import com.example.mypsychologist.domain.entity.ManagerEntity
import com.example.mypsychologist.domain.entity.TaskEntity

fun ManagerModel.toEntity() =
    ManagerEntity(username, company)

fun ManagerEntity.toModel() =
    ManagerModel(username = username, company = company)

fun TaskModel.toEntity() =
    TaskEntity(text, isCompleted, userId)
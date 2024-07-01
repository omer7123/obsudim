package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.ManagerModel
import com.example.mypsychologist.data.model.TaskModel
import com.example.mypsychologist.domain.entity.TaskEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity

fun ManagerModel.toEntity() =
    ManagerEntity(username, company, city, company, gender, birthDate)

fun ManagerEntity.toModel() =
    ManagerModel(username = username, company = company)

fun TaskModel.toEntity() =
    TaskEntity(text, isCompleted, userId)
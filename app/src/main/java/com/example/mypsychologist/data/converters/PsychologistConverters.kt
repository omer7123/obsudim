package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.ManagerModel
import com.example.mypsychologist.data.model.SendRequestToPsychologistModel
import com.example.mypsychologist.data.model.TaskModel
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.SendRequestToPsychologistEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.TaskEntity

fun ManagerModel.toEntity() =
    ManagerEntity(
        id,
        gender,
        username,
        birthDate,
        email,
        description,
        city,
        isActive,
        company,
        online,
        faceToFace
    )

fun SendRequestToPsychologistEntity.toModel() = SendRequestToPsychologistModel(id, text)

fun TaskModel.toEntity() =
    TaskEntity(id, text, userId, psychologistId, testTitle, testId, isCompleted, testDescription)

//fun ManagerEntity.toModel() =
//    ManagerModel(username = username, company = company)
//
//fun TaskModel.toEntity() =
//    TaskEntity(text, isCompleted, userId)
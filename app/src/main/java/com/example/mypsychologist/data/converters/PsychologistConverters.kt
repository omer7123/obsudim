package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.ManagerModel
import com.example.mypsychologist.data.model.SendRequestToPsychologistModel
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.SendRequestToPsychologistEntity

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

//fun ManagerEntity.toModel() =
//    ManagerModel(username = username, company = company)
//
//fun TaskModel.toEntity() =
//    TaskEntity(text, isCompleted, userId)
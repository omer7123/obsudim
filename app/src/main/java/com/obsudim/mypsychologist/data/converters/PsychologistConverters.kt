package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.ManagerModel
import com.obsudim.mypsychologist.data.model.MyPsychologistModel
import com.obsudim.mypsychologist.data.model.SendRequestToPsychologistModel
import com.obsudim.mypsychologist.data.model.TaskModel
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.MyPsychologistEntity
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.SendRequestToPsychologistEntity
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.TaskEntity

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
    TaskEntity(id, text, psychologistId, testTitle, testId, isCompleted, testDescription)

fun MyPsychologistModel.toEntity() = MyPsychologistEntity(id, isActive, request, role, username)

//fun ManagerEntity.toModel() =
//    ManagerModel(username = username, company = company)
//
//fun TaskModel.toEntity() =
//    TaskEntity(text, isCompleted, userId)
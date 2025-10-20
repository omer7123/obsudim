package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.UserDataModel
import com.obsudim.mypsychologist.data.model.UserInfoModel
import com.obsudim.mypsychologist.domain.entity.ClientInfoEntity
import com.obsudim.mypsychologist.domain.entity.TagEntity
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserDataEntity

fun ClientInfoEntity.toModel() =
    UserInfoModel(
        birthDate = birthday,
        gender = gender,
        username = name,
        request = request.map { it.id },
        city = city,
        description = "",
        type = TYPE_USER
    )

fun UserInfoModel.toEntity() =
    ClientInfoEntity(
        birthday = birthDate,
        gender = gender,
        name = username,
        city = city,
        request = request.map { TagEntity(it, "") } // Переделать
    )

fun UserDataModel.toEntity() =
    UserDataEntity(
        id,
        username,
        email,
        city,
        company,
        online,
        gender,
        birthDate,
        phoneNumber,
        description,
        isActive,
        department,
        faceToFace,
        roleId
    )

const val TYPE_USER = 1

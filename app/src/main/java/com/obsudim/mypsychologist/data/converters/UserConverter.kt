package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.UserDataModel
import com.obsudim.mypsychologist.data.model.UserInfoModel
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserDataEntity
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserInfoEntity

fun UserInfoEntity.toModel() =
    UserInfoModel(
        birthDate = birthday,
        gender = gender,
        username = name,
        city = city,
        description = description,
        company = company,
        online = true,
        phoneNumber = phone
    )

fun UserInfoModel.toEntity() =
    UserInfoEntity(
        birthday = birthDate,
        gender = gender,
        name = username,
        city = city,
        description = description,
        company = company,
        phone = phoneNumber
    )

fun UserDataModel.toEntity() =
    UserDataEntity(
        id,
        username,
        email,
        city,
        company,
        true,
        gender,
        birthDate,
        phoneNumber,
        description,
        true,
        department,
        faceToFace,
        roleId
    )

const val TYPE_USER = 1

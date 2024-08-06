package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.UserInfoModel
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.entity.TagEntity

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


const val TYPE_USER = 1

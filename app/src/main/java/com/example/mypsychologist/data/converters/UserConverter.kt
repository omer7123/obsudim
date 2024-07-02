package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.UserInfoModel
import com.example.mypsychologist.domain.entity.ClientInfoEntity

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


const val TYPE_USER = 1

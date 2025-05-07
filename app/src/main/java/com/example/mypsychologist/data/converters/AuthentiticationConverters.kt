package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.domain.entity.authenticationEntity.RegisterEntity

fun RegisterEntity.toModel(): RegisterModel{
    return RegisterModel(
        username = username,
        birthDate = birthDate,
        gender = gender.name,
        city = city,
        email = email,
        phoneNumber = phoneNumber,
        password = password,
        confirmPassword = confirmPassword
    )
}
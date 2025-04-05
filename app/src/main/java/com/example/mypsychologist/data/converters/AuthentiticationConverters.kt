package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.UserModel
import com.example.mypsychologist.domain.entity.authenticationEntity.Auth
import com.example.mypsychologist.domain.entity.authenticationEntity.RegisterEntity
import com.example.mypsychologist.domain.entity.authenticationEntity.User

fun AuthModel.toAuth(): Auth {
    return Auth(
        email = this.email,
        password = this.password
    )
}

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

fun UserModel.toUser(): User {
    return User(
        user_id = this.userId,
        email = this.email,
        username = this.username,
        token = this.token,
        role = this.role
    )
}

fun Auth.toAuthModel(): AuthModel {
    return AuthModel(
        email = this.email,
        password = this.password
    )
}


fun User.toUserModel(): UserModel {
    return UserModel(
        userId = this.user_id,
        email = this.email,
        username = this.username,
        token = this.token,
        role = this.role
    )
}
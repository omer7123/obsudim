package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.UserModel
import com.example.mypsychologist.domain.entity.authenticationEntity.Auth
import com.example.mypsychologist.domain.entity.authenticationEntity.Register
import com.example.mypsychologist.domain.entity.authenticationEntity.User

fun AuthModel.toAuth(): Auth {
    return Auth(
        email = this.email,
        password = this.password
    )
}

fun RegisterModel.toRegister(): Register {
    return Register(
        auth = this.auth.toAuth(),
        checkPassword = this.checkPassword
    )
}

fun UserModel.toUser(): User {
    return User(
        id = this.id,
        email = this.email,
        username = this.username,
        photo = this.photo,
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

fun Register.toRegisterModel(): RegisterModel {
    return RegisterModel(
        auth = this.auth.toAuthModel(),
        checkPassword = this.checkPassword
    )
}

fun User.toUserModel(): UserModel {
    return UserModel(
        id = this.id,
        email = this.email,
        username = this.username,
        photo = this.photo,
        token = this.token,
        role = this.role
    )
}
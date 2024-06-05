package com.example.mypsychologist.data.remote

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.data.model.UserModel
import com.example.mypsychologist.domain.entity.authenticationEntity.User

interface AuthenticationDataSource {
    suspend fun register(registerModel: RegisterModel): Resource<UserModel>
    suspend fun registerOld(registerModel: OldRegister): Resource<User> // старый
    suspend fun authOld(authModel: AuthModel): Resource<User> // старый
    suspend fun authByToken(token: Token): Resource<User>
}
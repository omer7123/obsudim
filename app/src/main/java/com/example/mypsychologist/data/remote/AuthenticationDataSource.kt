package com.example.mypsychologist.data.remote

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.data.model.UserModel
import com.example.mypsychologist.domain.entity.authenticationEntity.User

interface AuthenticationDataSource {

    suspend fun registerOld(registerModel: OldRegister): Resource<User>
    suspend fun authOld(authModel: AuthModel): Resource<User>
    suspend fun authByToken(token: Token): Resource<User>
}
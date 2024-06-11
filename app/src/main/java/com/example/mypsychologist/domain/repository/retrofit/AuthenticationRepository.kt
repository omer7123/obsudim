package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.entity.authenticationEntity.Register
import com.example.mypsychologist.domain.entity.authenticationEntity.User

interface AuthenticationRepository {

    suspend fun oldRegister(register: OldRegister): Resource<User>
    suspend fun authOld(authModel: AuthModel): Resource<User>
    suspend fun saveToken(token: String)
    suspend fun getToken(): String
    suspend fun authByToken(token: Token): Resource<User>

}
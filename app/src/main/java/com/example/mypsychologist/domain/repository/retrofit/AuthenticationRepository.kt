package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.entity.authenticationEntity.RegisterEntity
import com.example.mypsychologist.domain.entity.authenticationEntity.Tokens

interface AuthenticationRepository {
    suspend fun oldRegister(register: RegisterEntity): Resource<Tokens>
    suspend fun authOld(authModel: AuthModel): Resource<Tokens>
    suspend fun saveToken(token: String)
    suspend fun getToken(): String
    suspend fun deleteToken()
    suspend fun deleteUserId()
    suspend fun authByToken(token: Token): Resource<Tokens>
    suspend fun saveUserId(userId: String)
    suspend fun getUserId(): String

}
package com.example.mypsychologist.data.remote.authentication

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.domain.entity.authenticationEntity.RefreshToken
import com.example.mypsychologist.domain.entity.authenticationEntity.Tokens
import retrofit2.Response


interface AuthenticationDataSource {
    suspend fun registerOld(registerModel: RegisterModel): Resource<Tokens>
    suspend fun authOld(authModel: AuthModel): Resource<Tokens>
    suspend fun authByToken(token: String): Resource<Tokens>
    suspend fun refreshToken(refreshToken: RefreshToken): Response<Tokens>
}
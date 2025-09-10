package com.example.mypsychologist.data.remote.authentication

import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.entity.authenticationEntity.Tokens
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/auth/register")
    suspend fun register(@Body register: RegisterModel): Response<Tokens>
    @POST("/auth/auth")
    suspend fun authOld(@Body auhModel: AuthModel): Response<User>
    @POST("/auth/token-auth")
    suspend fun authByToken(@Body token: Token): Response<User>
}
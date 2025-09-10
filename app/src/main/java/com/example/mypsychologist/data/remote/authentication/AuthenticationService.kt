package com.example.mypsychologist.data.remote.authentication

import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.domain.entity.authenticationEntity.Tokens
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthenticationService {
    @POST("/auth/register")
    suspend fun register(@Body register: RegisterModel): Response<Tokens>
    @POST("/auth/login")
    suspend fun authOld(@Body auhModel: AuthModel): Response<Tokens>
    @POST("/auth/token-auth")
    suspend fun authByToken(@Query("token") token: String): Response<Tokens>
}
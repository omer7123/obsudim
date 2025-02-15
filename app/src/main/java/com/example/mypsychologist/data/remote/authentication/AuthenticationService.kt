package com.example.mypsychologist.data.remote.authentication

import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/users/reg")
    suspend fun register(@Body register: RegisterModel): Response<User>
    @POST("/users/auth")
    suspend fun authOld(@Body auhModel: AuthModel): Response<User>
    @POST("/users/auth_token")
    suspend fun authByToken(@Body token: Token): Response<User>
}
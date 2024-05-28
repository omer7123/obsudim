package com.example.mypsychologist.data.remote

import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.data.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/api/users/register")
    suspend fun register(@Body register: RegisterModel): Response<UserModel>

    @POST("/users/reg") //Старый бэк
    suspend fun registerOld(@Body register: OldRegister): Response<Token>
}
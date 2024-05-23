package com.example.mypsychologist.data.remote

import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.UserModel
import retrofit2.Response
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/api/users/register")
    suspend fun register(register: RegisterModel): Response<UserModel>
}
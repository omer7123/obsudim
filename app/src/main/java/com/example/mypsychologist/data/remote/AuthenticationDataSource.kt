package com.example.mypsychologist.data.remote

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.UserModel

interface AuthenticationDataSource {
    suspend fun register(registerModel: RegisterModel): Resource<UserModel>
}
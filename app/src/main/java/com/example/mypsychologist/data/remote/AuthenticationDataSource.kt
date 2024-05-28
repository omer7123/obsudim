package com.example.mypsychologist.data.remote

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.data.model.UserModel

interface AuthenticationDataSource {
    suspend fun register(registerModel: RegisterModel): Resource<UserModel>
    suspend fun registerOld(registerModel: OldRegister): Resource<Token> // старый
}
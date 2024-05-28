package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.entity.authenticationEntity.Register
import com.example.mypsychologist.domain.entity.authenticationEntity.User

interface AuthenticationRepository {

    suspend fun register(register: Register): Resource<User>
    suspend fun oldRegister(register: OldRegister): Resource<Token>
    suspend fun saveToken(token: String)
}
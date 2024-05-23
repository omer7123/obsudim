package com.example.mypsychologist.data.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toRegisterModel
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.remote.AuthenticationDataSource
import com.example.mypsychologist.domain.entity.authenticationEntity.Register
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(val dataSource: AuthenticationDataSource, val localDataSource: AuthenticationSharedPrefDataSource) :
    AuthenticationRepository {
    override suspend fun register(register: Register): Resource<User> {
        val result = dataSource.register(registerModel = register.toRegisterModel())
        val returnResult: Resource<User> = when (result) {
            is Resource.Success -> Resource.Success(result.data)
            is Resource.Error -> Resource.Error(result.msg, result.data)
            Resource.Loading -> Resource.Loading
        }
        return returnResult
    }

    override suspend fun saveToken(token: String) {
        localDataSource.saveToken(token)
    }
}
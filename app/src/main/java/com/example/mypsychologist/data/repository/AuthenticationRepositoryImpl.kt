package com.example.mypsychologist.data.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toRegisterModel
import com.example.mypsychologist.data.converters.toUser
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.data.remote.AuthenticationDataSource
import com.example.mypsychologist.domain.entity.authenticationEntity.Register
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val dataSource: AuthenticationDataSource,
    private val localDataSource: AuthenticationSharedPrefDataSource
) :
    AuthenticationRepository {
    override suspend fun register(register: Register): Resource<User> {
        val result = dataSource.register(registerModel = register.toRegisterModel())

        val returnResult: Resource<User> = when (result) {
            is Resource.Success -> Resource.Success(result.data.toUser())
            is Resource.Error -> Resource.Error(result.msg, result.data?.toUser())
            Resource.Loading -> Resource.Loading
        }
        return returnResult
    }

    override suspend fun oldRegister(register: OldRegister): Resource<User> {
        return dataSource.registerOld(register)
    }

    override suspend fun authOld(authModel: AuthModel): Resource<User> {
        return dataSource.authOld(authModel)
    }

    override suspend fun authByToken(token: Token): Resource<User> {
       return dataSource.authByToken(token)
    }

    override suspend fun saveToken(token: String) {
        localDataSource.saveToken(token)
    }

    override suspend fun getToken(): String {
        return localDataSource.getToken()
    }

}
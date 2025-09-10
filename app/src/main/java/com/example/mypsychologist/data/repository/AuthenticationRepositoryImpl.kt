package com.example.mypsychologist.data.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toModel
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.data.remote.authentication.AuthenticationDataSource
import com.example.mypsychologist.domain.entity.authenticationEntity.RegisterEntity
import com.example.mypsychologist.domain.entity.authenticationEntity.Tokens
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val dataSource: AuthenticationDataSource,
    private val localDataSource: AuthenticationSharedPrefDataSource
) :
    AuthenticationRepository {

    override suspend fun oldRegister(register: RegisterEntity): Resource<Tokens> {
        return dataSource.registerOld(register.toModel())
    }

    override suspend fun authOld(authModel: AuthModel): Resource<Tokens> {
        return dataSource.authOld(authModel)
    }

    override suspend fun authByToken(token: Token): Resource<Tokens> {
       return dataSource.authByToken(token.token)
    }

    override suspend fun saveUserId(userId: String) {
        localDataSource.saveUserId(userId)
    }

    override suspend fun getUserId(): String {
        return localDataSource.getUserId()
    }

    override suspend fun saveToken(token: String) {
        localDataSource.saveToken(token)
    }

    override suspend fun getToken(): String {
        return localDataSource.getToken()
    }

    override suspend fun deleteToken() {
        localDataSource.deleteToken()
    }

    override suspend fun deleteUserId() {
        localDataSource.deleteUserId()
    }

}
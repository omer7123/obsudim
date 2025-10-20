package com.obsudim.mypsychologist.data.repository

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.converters.toModel
import com.obsudim.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.obsudim.mypsychologist.data.model.AuthModel
import com.obsudim.mypsychologist.data.model.Token
import com.obsudim.mypsychologist.data.remote.authentication.AuthenticationDataSource
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.RegisterEntity
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.Tokens
import com.obsudim.mypsychologist.domain.repository.retrofit.AuthenticationRepository
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

    override suspend fun saveRefToken(refToken: String) {
        localDataSource.saveRefreshToken(refToken)
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
package com.obsudim.mypsychologist.data.remote.authentication

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.AuthModel
import com.obsudim.mypsychologist.data.model.RegisterModel
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.RefreshToken
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.Tokens
import retrofit2.Response
import javax.inject.Inject

class AuthenticationDataSourceImpl @Inject constructor(val api: AuthenticationService) :
    AuthenticationDataSource, BaseDataSource() {

    override suspend fun registerOld(registerModel: RegisterModel): Resource<Tokens> = getResult {
        api.register(registerModel)
    }

    override suspend fun authOld(authModel: AuthModel): Resource<Tokens> = getResult {
        api.authOld(authModel)
    }

    override suspend fun authByToken(token: String): Resource<Tokens> = getResult {
        api.authByToken(token)
    }

    override suspend fun refreshToken(refreshToken: RefreshToken): Response<Tokens> {
        return api.refreshToken(refreshToken)
    }
}
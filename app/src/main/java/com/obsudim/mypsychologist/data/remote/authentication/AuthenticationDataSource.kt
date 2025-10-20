package com.obsudim.mypsychologist.data.remote.authentication

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.AuthModel
import com.obsudim.mypsychologist.data.model.RegisterModel
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.RefreshToken
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.Tokens
import retrofit2.Response


interface AuthenticationDataSource {
    suspend fun registerOld(registerModel: RegisterModel): Resource<Tokens>
    suspend fun authOld(authModel: AuthModel): Resource<Tokens>
    suspend fun authByToken(token: String): Resource<Tokens>
    suspend fun refreshToken(refreshToken: RefreshToken): Response<Tokens>
}
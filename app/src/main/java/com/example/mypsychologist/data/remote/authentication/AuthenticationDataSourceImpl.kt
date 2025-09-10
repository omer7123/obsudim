package com.example.mypsychologist.data.remote.authentication

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.entity.authenticationEntity.Tokens
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import javax.inject.Inject

class AuthenticationDataSourceImpl @Inject constructor(val api: AuthenticationService) :
    AuthenticationDataSource, BaseDataSource() {

    override suspend fun registerOld(registerModel: RegisterModel): Resource<Tokens> = getResult {
        api.register(registerModel)
    }

    override suspend fun authOld(authModel: AuthModel): Resource<User> = getResult {
        api.authOld(authModel)
    }

    override suspend fun authByToken(token: Token): Resource<User> = getResult {
        api.authByToken(token)
    }
}
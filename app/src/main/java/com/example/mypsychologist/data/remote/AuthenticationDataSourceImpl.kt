package com.example.mypsychologist.data.remote

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.data.model.RegisterModel
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.data.model.UserModel
import javax.inject.Inject

class AuthenticationDataSourceImpl @Inject constructor(val api: AuthenticationService) :
    AuthenticationDataSource, BaseDataSource() {
    override suspend fun register(registerModel: RegisterModel): Resource<UserModel> = getResult {
        api.register(registerModel)
    }

    override suspend fun registerOld(registerModel: OldRegister): Resource<Token> = getResult {
        api.registerOld(registerModel)
    }
}
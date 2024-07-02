package com.example.mypsychologist.data.remote.profile

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.data.model.UserInfoModel

interface UserDataSource {
    suspend fun updateUser(info: UserInfoModel): Resource<String>

    //   suspend fun getUserData(token: Token): Resource<UserInfo>
}

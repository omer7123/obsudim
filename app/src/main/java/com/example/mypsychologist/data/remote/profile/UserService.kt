package com.example.mypsychologist.data.remote.profile

import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.data.model.UserInfoModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST
    fun updateUser(@Body userInfo: UserInfoModel, @Body token: Token): Response<String>
}

package com.obsudim.mypsychologist.data.remote.profile

import com.obsudim.mypsychologist.data.model.SendRequestToPsychologistModel
import com.obsudim.mypsychologist.data.model.UserDataModel
import com.obsudim.mypsychologist.data.model.UserInfoModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserService {
    @PATCH("/auth/update")
    suspend fun updateUser(@Body userInfo: UserInfoModel): Response<String>

    @GET("/auth/me")
    suspend fun getOwnData(): Response<UserInfoModel>

    @POST("/client/send_application")
    suspend fun sendRequestToManager(@Body requestToPsychologist: SendRequestToPsychologistModel): Response<String>

    @GET("/auth/me")
    suspend fun getAuthMe(): Response<UserDataModel>

    @DELETE("/auth/delete")
    suspend fun deleteAccount(): Response<String>
}

package com.obsudim.mypsychologist.data.remote.profile

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.SendRequestToPsychologistModel
import com.obsudim.mypsychologist.data.model.UserDataModel
import com.obsudim.mypsychologist.data.model.UserInfoModel
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun updateUser(info: UserInfoModel): Resource<String>
    suspend fun getOwnData(): Resource<UserInfoModel>
    suspend fun sendRequestToManager(sendRequestToPsychologistModel: SendRequestToPsychologistModel): Resource<String>
    suspend fun getAuthMe(): Flow<Resource<UserDataModel>>

    //   suspend fun getUserData(token: Token): Resource<UserInfo>
}

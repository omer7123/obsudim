package com.example.mypsychologist.data.remote.profile

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.SendRequestToPsychologistModel
import com.example.mypsychologist.data.model.UserInfoModel
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val api: UserService) :
    UserDataSource, BaseDataSource() {
    override suspend fun updateUser(info: UserInfoModel): Resource<String> =
        getResult {
            api.updateUser(info)
        }

    override suspend fun getOwnData(): Resource<UserInfoModel> = getResult {
        api.getOwnData()
    }

    override suspend fun sendRequestToManager(sendRequestToPsychologistModel: SendRequestToPsychologistModel): Resource<String> = getResult{
        api.sendRequestToManager(sendRequestToPsychologistModel)
    }
}

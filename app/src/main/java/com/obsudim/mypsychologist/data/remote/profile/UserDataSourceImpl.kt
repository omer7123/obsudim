package com.obsudim.mypsychologist.data.remote.profile

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.SendRequestToPsychologistModel
import com.obsudim.mypsychologist.data.model.UserDataModel
import com.obsudim.mypsychologist.data.model.UserInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    override suspend fun getAuthMe(): Flow<Resource<UserDataModel>> = flow {
        emit(Resource.Loading)
        emit(
            getResult {
                api.getAuthMe()
            }
        )
    }.flowOn(Dispatchers.IO)
}

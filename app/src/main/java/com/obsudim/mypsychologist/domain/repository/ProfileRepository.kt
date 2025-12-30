package com.obsudim.mypsychologist.domain.repository

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.ClientInfoEntity
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserDataEntity
import com.obsudim.mypsychologist.domain.entity.priofileEntity.UserInfoEntity
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.SendRequestToPsychologistEntity
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun saveClient(info: UserInfoEntity): Resource<Unit>
    suspend fun getOwnInfo(): Resource<UserInfoEntity>
    suspend fun sendRequestToPsychologist(sendRequestToPsychologistEntity: SendRequestToPsychologistEntity): Resource<String>
    suspend fun getAuthMe(): Flow<Resource<UserDataEntity>>
    suspend fun deleteAccount(): Flow<Resource<String>>
}
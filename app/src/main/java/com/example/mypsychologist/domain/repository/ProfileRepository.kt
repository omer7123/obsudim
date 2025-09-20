package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.entity.priofileEntity.UserDataEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.SendRequestToPsychologistEntity
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun saveClient(info: ClientInfoEntity): Resource<String>
    suspend fun getOwnInfo(): Resource<ClientInfoEntity>
    suspend fun sendRequestToPsychologist(sendRequestToPsychologistEntity: SendRequestToPsychologistEntity): Resource<String>
    suspend fun getAuthMe(): Flow<Resource<UserDataEntity>>
}
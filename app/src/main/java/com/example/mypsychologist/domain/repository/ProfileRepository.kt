package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.SendRequestToPsychologistEntity

interface ProfileRepository {
    suspend fun saveClient(info: ClientInfoEntity): Resource<String>
    suspend fun getOwnInfo(): Resource<ClientInfoEntity>
    suspend fun sendFeedback(text: String): Boolean
    suspend fun sendRequestToPsychologist(sendRequestToPsychologistEntity: SendRequestToPsychologistEntity): Resource<String>

}
package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.*
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.SendRequestToPsychologistEntity

interface PsychologistRepository {
//    suspend fun getPsychologists(): HashMap<String, PsychologistCard>
//    suspend fun getOwnPsychologists(): HashMap<String, PsychologistWithTaskCount>
//    suspend fun getPsychologist(id: String): PsychologistData
//    fun sendRequestTo(psychologistId: String, text: String): Boolean
//    suspend fun getClientsRequests(): List<ClientRequestEntity>
//    suspend fun sendAnswerToRequest(accept: Boolean, clientId: String): Boolean
//    suspend fun getTasks(psychologistId: String): HashMap<String, TaskEntity>
//    fun markTaskAsCompleted(taskId: String, psychologistId: String): Boolean
//    fun markTaskAsNotCompleted(taskId: String, psychologistId: String): Boolean

    suspend fun getOwnPsychologists(): Resource<List<ManagerEntity>>
    suspend fun sendRequestToPsychologist(sendRequestToPsychologistEntity: SendRequestToPsychologistEntity): Resource<String>
}
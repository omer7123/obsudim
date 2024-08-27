package com.example.mypsychologist.domain.repository.retrofit

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.MyPsychologistEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.TaskEntity

interface PsychologistRepository {
//    suspend fun getPsychologists(): HashMap<String, PsychologistCard>
//    suspend fun getOwnPsychologists(): HashMap<String, PsychologistWithTaskCount>
//    suspend fun getPsychologist(id: String): PsychologistData
//    fun sendRequestTo(psychologistId: String, text: String): Boolean
//    suspend fun getClientsRequests(): List<ClientRequestEntity>
//    suspend fun sendAnswerToRequest(accept: Boolean, clientId: String): Boolean
    suspend fun getTasks(): Resource<List<TaskEntity>>

    suspend fun getYourPsychologists(): Resource<List<MyPsychologistEntity>>
    suspend fun markTaskAsCompleted(taskId: String): Resource<String>
    suspend fun markTaskAsNotCompleted(taskId: String): Resource<String>

    suspend fun getOwnPsychologists(): Resource<List<ManagerEntity>>

    suspend fun getStatusToRequestManager(): Boolean
}
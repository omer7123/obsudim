package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.*

interface PsychologistRepository {
    suspend fun getPsychologists(): HashMap<String, PsychologistCard>
    suspend fun getPsychologist(id: String): PsychologistData
    fun sendRequestTo(psychologistId: String, text: String): Boolean
    suspend fun getClientsRequests(): List<ClientRequestEntity>
    suspend fun sendAnswerToRequest(accept: Boolean, clientId: String): Boolean

}
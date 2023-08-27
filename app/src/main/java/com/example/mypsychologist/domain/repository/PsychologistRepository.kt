package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.ClientRequestEntity
import com.example.mypsychologist.domain.entity.PsychologistCard
import com.example.mypsychologist.domain.entity.PsychologistData

interface PsychologistRepository {
    suspend fun getPsychologists(): HashMap<String, PsychologistCard>
    suspend fun getPsychologist(id: String): PsychologistData
    fun sendRequestTo(psychologistId: String, text: String): Boolean
    suspend fun getClientsRequests(): List<ClientRequestEntity>
    suspend fun sendAnswerToRequest(accept: Boolean, clientId: String): Boolean
}
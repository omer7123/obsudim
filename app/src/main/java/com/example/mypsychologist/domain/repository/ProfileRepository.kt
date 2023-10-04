package com.example.mypsychologist.domain.repository

import android.net.Uri
import com.example.mypsychologist.domain.entity.*

interface ProfileRepository {
    suspend fun deleteAccount(): Boolean
    fun sendFeedback(text: String): Boolean
    fun savePsychologist(info: PsychologistInfo): Boolean
    fun savePsychologist(docs: List<Uri>): Boolean
    suspend fun getClientData(): ClientDataEntity
    fun changeName(it: String): Boolean
    fun changeBirthday(it: Long): Boolean
    fun changeGender(it: String): Boolean
    fun changeDiagnosis(it: String): Boolean
    fun changeRequest(it: List<String>): Boolean
    suspend fun changeMail(it: String): Boolean
    suspend fun changePhone(it: String): Boolean
    suspend fun changePassword(it: String): Boolean
    suspend fun checkIfPsychologist(): Boolean
    suspend fun getClients(): List<ClientCardEntity>
    suspend fun getClientInfo(clientId: String): ClientInfoEntity
    suspend fun getClientTasks(clientId: String): HashMap<String, TaskEntity>
    suspend fun newTask(task: String, clientId: String): String
    suspend fun deleteTask(taskId: String, clientId: String)
}
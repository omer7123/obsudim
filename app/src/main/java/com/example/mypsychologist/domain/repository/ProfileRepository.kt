package com.example.mypsychologist.domain.repository

import android.net.Uri
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ClientCardEntity
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.entity.PsychologistInfo
import com.example.mypsychologist.domain.entity.TaskEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.SendRequestToPsychologistEntity

interface ProfileRepository {
    suspend fun saveClient(info: ClientInfoEntity): Resource<String>
    suspend fun getOwnInfo(): Resource<ClientInfoEntity>
    suspend fun deleteAccount(): Boolean
    suspend fun sendFeedback(text: String): Boolean
    suspend fun savePsychologist(info: PsychologistInfo): Boolean
    suspend fun savePsychologist(docs: List<Uri>): Boolean
    suspend fun changeRequest(it: List<String>): Boolean
    suspend fun changeMail(it: String): Boolean
    suspend fun changePhone(it: String): Boolean
    suspend fun changePassword(it: String): Boolean
    suspend fun checkIfPsychologist(): Boolean
    suspend fun getClients(): List<ClientCardEntity>
    suspend fun sendRequestToPsychologist(sendRequestToPsychologistEntity: SendRequestToPsychologistEntity): Resource<String>
    suspend fun getClientInfo(clientId: String): ClientInfoEntity
    suspend fun getClientTasks(clientId: String): HashMap<String, TaskEntity>
    suspend fun newTask(task: String, clientId: String): String
    suspend fun deleteTask(taskId: String, clientId: String)

}
package com.example.mypsychologist.domain.repository

import android.net.Uri
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.entity.PsychologistInfo

interface ProfileRepository {
    suspend fun deleteAccount(): Boolean
    fun sendFeedback(text: String): Boolean
    fun savePsychologist(info: PsychologistInfo): Boolean
    fun savePsychologist(docs: List<Uri>): Boolean
    suspend fun getClientInfo(): ClientInfoEntity
    fun changeName(it: String): Boolean
    fun changeBirthday(it: Long): Boolean
    fun changeGender(it: String): Boolean
    fun changeDiagnosis(it: String): Boolean
    fun changeRequest(it: List<String>): Boolean
    suspend fun changeMail(it: String): Boolean
    suspend fun changePhone(it: String): Boolean
    suspend fun changePassword(it: String): Boolean
}
package com.example.mypsychologist.domain.repository

import android.net.Uri
import com.example.mypsychologist.domain.entity.PsychologistInfo

interface ProfileRepository {
    suspend fun deleteAccount(): Boolean
    fun sendFeedback(text: String): Boolean
    fun savePsychologist(info: PsychologistInfo): Boolean
    fun savePsychologist(docs: List<Uri>): Boolean
}
package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.PsychologistCard

interface PsychologistRepository {
    suspend fun getPsychologist(): HashMap<String, PsychologistCard>
}
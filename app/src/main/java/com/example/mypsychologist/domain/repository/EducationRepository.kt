package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.EducationTopicEntity

interface EducationRepository {
    suspend fun getProgress(topic: String): Int
    suspend fun getTopics(): List<EducationTopicEntity>
}
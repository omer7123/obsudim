package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.EducationTopicEntity
import com.example.mypsychologist.domain.entity.educationEntity.ThemeEntity

interface EducationRepository {
    suspend fun getAllTheme(): Resource<List<ThemeEntity>>
    suspend fun getProgress(topic: String): Int
    suspend fun getTopics(): List<EducationTopicEntity>
    suspend fun saveProgress(topic: String, progress: Int)
    fun getBase(): Int
    fun getCBTBase(): Int
    fun getBurnout(): Int
    fun getBreathingTechniques(): Int
    fun getRelaxationTechniques(): Int
    fun getCopingStrategies():Int
}
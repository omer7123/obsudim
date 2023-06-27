package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity
import kotlinx.coroutines.flow.Flow

interface ExercisesRepository {
    fun getREBTProblemProgress(problem: String): RebtProblemProgressEntity
}
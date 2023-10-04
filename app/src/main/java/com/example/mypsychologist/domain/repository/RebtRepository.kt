package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity
import kotlinx.coroutines.flow.Flow

interface RebtRepository {
    fun getREBTProblemProgress(problemId: Int): RebtProblemProgressEntity
    fun getCurrentREBTProblemProgress(): RebtProblemProgressEntity
    fun getREBTProblems(): List<ProblemEntity>
}
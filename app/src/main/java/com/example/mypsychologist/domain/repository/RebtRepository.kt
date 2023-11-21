package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.domain.entity.AutoDialogMessageEntity
import com.example.mypsychologist.domain.entity.BeliefAnalysisEntity
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity

interface RebtRepository {
    suspend fun getREBTProblemProgress(problemId: String): RebtProblemProgressEntity
    suspend fun getCurrentREBTProblemProgress(): RebtProblemProgressEntity?
    suspend fun getREBTProblems(): HashMap<String,ProblemEntity>
    suspend fun saveProblem(problemEntity: ProblemEntity): String
    suspend fun saveCurrentProblem(id: String): Boolean
    suspend fun saveAnalysis(rebtAnalysisEntity: ProblemAnalysisEntity): Boolean
    suspend fun getProblemAnalysis(): ProblemAnalysisEntity
    suspend fun saveBeliefVerification(it: BeliefVerificationEntity, type: String): Boolean
    suspend fun saveBeliefAnalysis(it: BeliefAnalysisEntity, type: String): Boolean
    suspend fun saveDialogMessage(it: AutoDialogMessageEntity): String
    suspend fun getAutoDialog(): HashMap<String, AutoDialogMessageEntity>
}
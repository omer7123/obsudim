package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.AutoDialogMessageEntity
import com.example.mypsychologist.domain.entity.BeliefAnalysisEntity
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity

interface RebtRepository {
    suspend fun getREBTProblemProgress(problemId: String): RebtProblemProgressEntity?
    suspend fun getCurrentREBTProblemProgress(): RebtProblemProgressEntity?
    suspend fun getREBTProblems(): Resource<List<ProblemEntity>>
    suspend fun saveProblem(problemEntity: ProblemEntity): Resource<String>
    suspend fun saveCurrentProblem(id: String): Boolean
    suspend fun saveAnalysis(rebtAnalysisEntity: ProblemAnalysisEntity): Boolean
    suspend fun getProblemAnalysis(): ProblemAnalysisEntity
    suspend fun saveBeliefVerification(it: BeliefVerificationEntity, type: String): Resource<String>
    suspend fun getBeliefVerification(problemId: String): Resource<BeliefVerificationEntity>
    suspend fun saveBeliefAnalysis(it: BeliefAnalysisEntity, type: String): Resource<String>
    suspend fun getBeliefAnalysis(type: String): BeliefAnalysisEntity
    suspend fun saveDialogMessage(it: AutoDialogMessageEntity): String
    suspend fun getAutoDialog(): HashMap<String, AutoDialogMessageEntity>
}
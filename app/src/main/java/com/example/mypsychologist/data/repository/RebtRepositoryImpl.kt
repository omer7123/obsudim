package com.example.mypsychologist.data.repository

import android.util.Log
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntities
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.converters.toModel
import com.example.mypsychologist.data.converters.toModels
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.remote.exercises.BeliefsDataSource
import com.example.mypsychologist.data.remote.exercises.ProblemDataSource
import com.example.mypsychologist.domain.entity.AutoDialogMessageEntity
import com.example.mypsychologist.domain.entity.BeliefAnalysisEntity
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import com.example.mypsychologist.extensions.getTypedValue
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RebtRepositoryImpl @Inject constructor(
    private val beliefsDataSource: BeliefsDataSource,
    private val problemDataSource: ProblemDataSource,
    private val localDataSource: AuthenticationSharedPrefDataSource
) :
    RebtRepository {

    override suspend fun getREBTProblemProgress(problemId: String): RebtProblemProgressEntity? =
        suspendCoroutine { continuation ->
            //TODO
        }

    override suspend fun getCurrentREBTProblemProgress(): RebtProblemProgressEntity? =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
//TODO
            try {
                getREBTProblemProgress(getCurrentProblemId()!!)
            } catch (t: Throwable) {
                null
            }
        }

    private suspend fun getCurrentProblemId(): String? =
        suspendCoroutine { continuation ->
//            TODO

        }

    override suspend fun getREBTProblems(): Resource<List<ProblemEntity>> =
        when(val result = problemDataSource.getProblems(localDataSource.getUserId())) {
            is Resource.Error -> {
                Log.d("Problem Error", result.msg.toString())
                Resource.Error(result.msg.toString(), null)
            }
            is Resource.Loading -> Resource.Loading
            is Resource.Success ->
                Resource.Success(result.data.toEntities())
        }


    override suspend fun saveProblem(problemEntity: ProblemEntity) =
        problemDataSource.save(problemEntity.toModel())

    override suspend fun saveCurrentProblem(id: String): Boolean =
        try {
            true
        } catch (t: Throwable) {
            false
        }


    override suspend fun saveAnalysis(
        problemAnalysis: ProblemAnalysisEntity
    ): List<Resource<String>> = run {
        val results = mutableListOf<Resource<String>>()
        problemAnalysis.toModels().forEach {item ->
            results.add(problemDataSource.saveProblemAnalysisItem(item))
        }

        results
    }

    override suspend fun getProblemAnalysis(): ProblemAnalysisEntity =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            loadProblemAnalysis(getCurrentProblemId()!!)
        }

    private suspend fun loadProblemAnalysis(problemId: String): ProblemAnalysisEntity =
        suspendCoroutine { continuation ->

        }

    override suspend fun saveBeliefVerification(
        it: BeliefVerificationEntity,
        type: String
    ): Resource<String> =
        beliefsDataSource.save(it.toModel())

    override suspend fun getBeliefVerification(problemId: String): Resource<BeliefVerificationEntity> = run {
        when(val result = beliefsDataSource.getBeliefCheck(problemId)) {
            is Resource.Error -> {
                Log.d("Belied check Error", result.msg.toString())
                Resource.Error(result.msg.toString(), null)
            }
            is Resource.Loading -> Resource.Loading
            is Resource.Success ->
                Resource.Success(result.data.toEntity())
        }
    }



    override suspend fun saveBeliefAnalysis(it: BeliefAnalysisEntity, type: String): Resource<String> =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            TODO()
        }

    override suspend fun getBeliefAnalysis(type: String): BeliefAnalysisEntity =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            loadBeliefAnalysis(getCurrentProblemId()!!, type)
        }

    private suspend fun loadBeliefAnalysis(
        problemId: String,
        type: String
    ): BeliefAnalysisEntity =
        suspendCoroutine { continuation ->

        }

    override suspend fun saveDialogMessage(it: AutoDialogMessageEntity): String = ""

    override suspend fun getAutoDialog(): HashMap<String, AutoDialogMessageEntity> =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            loadAutoDialog(getCurrentProblemId()!!)
        }

    private suspend fun loadAutoDialog(problemId: String): HashMap<String, AutoDialogMessageEntity> =
        suspendCoroutine { continuation ->

        }

    companion object {
        private const val CURRENT_REBT_PROBLEM = "CurrentRebtProblem"
    }
}
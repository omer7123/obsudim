package com.example.mypsychologist.data.repository

import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import com.example.mypsychologist.getTypedValue
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RebtRepositoryImpl @Inject constructor(private val reference: DatabaseReference) :
    RebtRepository {
    override suspend fun getREBTProblemProgress(problemId: String): RebtProblemProgressEntity =
        suspendCoroutine { continuation ->
            reference.child(RebtProblemProgressEntity::class.simpleName!!).child(problemId).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.getTypedValue()!!)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun getCurrentREBTProblemProgress(): RebtProblemProgressEntity? =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val currentProblemId = getCurrentProblemId()
            if (currentProblemId != null)
                getREBTProblemProgress(currentProblemId)
            else
                null
        }

    private suspend fun getCurrentProblemId(): String? =
        suspendCoroutine { continuation ->
            reference.child(CURRENT_REBT_PROBLEM).get()
                .addOnSuccessListener {
                    continuation.resume(it.getTypedValue())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun getREBTProblems(): HashMap<String, ProblemEntity> =
        suspendCoroutine { continuation ->
            reference.child(ProblemEntity::class.simpleName!!).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.getTypedValue() ?: HashMap())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun saveProblem(problemEntity: ProblemEntity) =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val ref = reference.child(ProblemEntity::class.simpleName!!)
            val key = ref.push().key!!

            ref.child(key).setValue(problemEntity)

            reference.child(RebtProblemProgressEntity::class.simpleName!!).child(key)
                .setValue(RebtProblemProgressEntity(problem = problemEntity.title))

            key
        }

    override suspend fun saveCurrentProblem(id: String): Boolean =
        try {
            reference.child(CURRENT_REBT_PROBLEM).setValue(id)
            reference.child(ProblemEntity::class.simpleName!!).child(id).child(ProblemEntity::actual.name).setValue(true)
            true
        } catch (t: Throwable) {
            false
        }


    override suspend fun saveAnalysis(
        problemId: String,
        rebtAnalysisEntity: ProblemAnalysisEntity
    ): Boolean =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            try {
                reference.child(ProblemAnalysisEntity::class.simpleName!!).child(problemId)
                    .setValue(rebtAnalysisEntity)

                reference.child(RebtProblemProgressEntity::class.simpleName!!).child(problemId)
                    .child(RebtProblemProgressEntity::problemAnalysisCompleted.name)

                true
            } catch (t: Throwable) {
                false
            }
        }

    companion object {
        private const val CURRENT_REBT_PROBLEM = "CurrentRebtProblem"
    }
}
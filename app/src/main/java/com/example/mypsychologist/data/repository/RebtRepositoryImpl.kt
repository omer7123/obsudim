package com.example.mypsychologist.data.repository

import android.util.Log
import com.example.mypsychologist.domain.entity.AutoDialogMessageEntity
import com.example.mypsychologist.domain.entity.BeliefAnalysisEntity
import com.example.mypsychologist.domain.entity.BeliefVerificationEntity
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import com.example.mypsychologist.getTypedValue
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
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
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val currentProblem = getCurrentProblemId()
            HashMap(
                loadProblems().map {
                    Pair(it.key, it.value.copy(actual = it.key == currentProblem))
                }.toMap()
            )
        }
    private suspend fun loadProblems(): HashMap<String, ProblemEntity> =
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
            true
        } catch (t: Throwable) {
            false
        }


    override suspend fun saveAnalysis(
        rebtAnalysisEntity: ProblemAnalysisEntity
    ): Boolean =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val problemId = getCurrentProblemId()!!
            try {
                reference.child(ProblemAnalysisEntity::class.simpleName!!).child(problemId)
                    .setValue(rebtAnalysisEntity)

                reference.child(RebtProblemProgressEntity::class.simpleName!!).child(problemId)
                    .child(RebtProblemProgressEntity::problemAnalysisCompleted.name).setValue(true)

                true
            } catch (t: Throwable) {
                false
            }
        }

    override suspend fun getProblemAnalysis(): ProblemAnalysisEntity =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            loadProblemAnalysis(getCurrentProblemId()!!)
        }

    private suspend fun loadProblemAnalysis(problemId: String): ProblemAnalysisEntity =
        suspendCoroutine { continuation ->
            reference.child(ProblemAnalysisEntity::class.simpleName!!).child(problemId).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.getTypedValue() ?: ProblemAnalysisEntity())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun saveBeliefVerification(
        it: BeliefVerificationEntity,
        type: String
    ): Boolean =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            try {
                val problemId = getCurrentProblemId()!!
                reference.child(BeliefVerificationEntity::class.simpleName!!).child(problemId)
                    .child(type).setValue(it)

                reference.child(RebtProblemProgressEntity::class.simpleName!!).child(problemId)
                    .child(RebtProblemProgressEntity::beliefsCheckCompleted.name)
                    .setValue(true)    // косяк

                true
            } catch (t: Throwable) {
                false
            }
        }

    override suspend fun saveBeliefAnalysis(it: BeliefAnalysisEntity, type: String): Boolean =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            try {
                val problemId = getCurrentProblemId()!!
                reference.child(BeliefAnalysisEntity::class.simpleName!!).child(problemId)
                    .child(type).setValue(it)

                reference.child(RebtProblemProgressEntity::class.simpleName!!).child(problemId)
                    .child(RebtProblemProgressEntity::beliefsAnalysisCompleted.name)
                    .setValue(true) // косяк

                true
            } catch (t: Throwable) {
                false
            }
        }

    override suspend fun saveDialogMessage(it: AutoDialogMessageEntity): String =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            val problemId = getCurrentProblemId()!!

            val ref = reference.child(AutoDialogMessageEntity::class.simpleName!!).child(problemId)
            val key = ref.push().key!!

            ref.child(key).setValue(it)

            reference.child(RebtProblemProgressEntity::class.simpleName!!).child(problemId)
                .child(RebtProblemProgressEntity::dialogCompleted.name)
                .setValue(true)

            key
        }

    override suspend fun getAutoDialog(): HashMap<String, AutoDialogMessageEntity> =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            loadAutoDialog(getCurrentProblemId()!!)
        }

    private suspend fun loadAutoDialog(problemId: String): HashMap<String, AutoDialogMessageEntity> =
        suspendCoroutine { continuation ->
            reference.child(AutoDialogMessageEntity::class.simpleName!!).child(problemId).get()
                .addOnSuccessListener { snapshot ->
                    val result = LinkedHashMap<String, AutoDialogMessageEntity>()
                    snapshot.children.forEach {
                        result[it.key.toString()] = AutoDialogMessageEntity(
                            (it.value as HashMap<String, Any>)[AutoDialogMessageEntity::rational.name] as Boolean,
                            (it.value as HashMap<String, Any>)[AutoDialogMessageEntity::message.name] as String
                        )
                    }
                    continuation.resume(result)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    companion object {
        private const val CURRENT_REBT_PROBLEM = "CurrentRebtProblem"
    }
}
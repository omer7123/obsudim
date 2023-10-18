package com.example.mypsychologist.data.repository

import com.example.mypsychologist.domain.entity.ProblemEntity
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

class RebtRepositoryImpl @Inject constructor(private val reference: DatabaseReference) : RebtRepository {
    override fun getREBTProblemProgress(problemId: Int) =
        RebtProblemProgressEntity("Проблема1", true, false, false, false)

    override fun getCurrentREBTProblemProgress() =
        RebtProblemProgressEntity("Проблема", true, false, false, false)

    override suspend fun getREBTProblems(): HashMap<String, ProblemEntity> =
        suspendCoroutine { continuation ->
            reference.child(ProblemEntity::class.simpleName!!).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.getTypedValue() ?: HashMap())
                }
                .addOnFailureListener{
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun saveProblem(problemEntity: ProblemEntity) =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                val ref = reference.child(ProblemEntity::class.simpleName!!)
                val key = ref.push().key!!

                ref.child(key).setValue(problemEntity)

                key
        }
}
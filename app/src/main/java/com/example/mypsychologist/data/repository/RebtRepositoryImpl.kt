package com.example.mypsychologist.data.repository

import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RebtRepositoryImpl @Inject constructor(private val reference: DatabaseReference) : RebtRepository {
    override fun getREBTProblemProgress(problemId: Int) =
        RebtProblemProgressEntity("Проблема1", true, false, false, false)

    override fun getCurrentREBTProblemProgress() =
        RebtProblemProgressEntity("Проблема", true, false, false, false)

    override fun getREBTProblems() = hashMapOf(
        "0" to ProblemEntity("Проблема 1"),
        "1" to ProblemEntity("Проблема 2"),
        "2" to ProblemEntity("Проблема 3"),
        "3" to ProblemEntity("Проблема 1"),
        "4" to ProblemEntity("Проблема 1")
    )

    override suspend fun saveProblem(problemEntity: ProblemEntity): String =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                val ref = reference.child(ProblemEntity::class.simpleName!!)
                val key = ref.push().key!!

                ref.child(key).setValue(problemEntity)

                key
        }
}
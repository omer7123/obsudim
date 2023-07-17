package com.example.mypsychologist.data.repository

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestResultEntity
import com.example.mypsychologist.domain.repository.DiagnosticRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DiagnosticsRepositoryImpl @Inject constructor(private val reference: DatabaseReference) :
    DiagnosticRepository {

    override fun saveBeckDepression(result: TestResultEntity, testTitle: String): Boolean =
        try {
            val ref = reference.child(DIAGNOSTIC_HISTORY).child(testTitle)
            val key = ref.push().key

            ref.child(key!!).setValue(result)

            true
        } catch (t: Throwable) {
            false
        }

    override suspend fun getTestResults(title: String): List<TestResultEntity> =
        suspendCoroutine { continuation ->

            reference.child(DIAGNOSTIC_HISTORY).child(title).get()
                .addOnSuccessListener {
                    val result = it.getValue(object :
                        GenericTypeIndicator<HashMap<String, TestResultEntity>>() {}) ?: HashMap()

                    continuation.resume(result.values.toList())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    companion object {
        private const val DIAGNOSTIC_HISTORY = "diagnostic history"
    }
}
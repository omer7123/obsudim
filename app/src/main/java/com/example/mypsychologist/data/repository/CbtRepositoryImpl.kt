package com.example.mypsychologist.data.repository

import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.repository.CbtRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CbtRepositoryImpl @Inject constructor(private val reference: DatabaseReference) :
    CbtRepository {

    override suspend fun getThoughtDiaries(): HashMap<String, String> =
        suspendCoroutine { continuation ->

            reference.child(THOUGHT_DIARIES_LIST).get()
                .addOnSuccessListener {
                    continuation.resume(
                        it.getValue(object : GenericTypeIndicator<HashMap<String, String>>() {})
                            ?: HashMap()
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }

        }

    override suspend fun getThoughtDiary(id: String): ThoughtDiaryEntity =
        suspendCoroutine { continuation ->

            reference.child(ThoughtDiaryEntity::class.simpleName!!).child(id).get()
                .addOnSuccessListener {
                    continuation.resume(
                        it.getValue(object : GenericTypeIndicator<ThoughtDiaryEntity>() {})
                            ?: ThoughtDiaryEntity()
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }

        }

    override fun saveThoughtDiary(it: ThoughtDiaryEntity): Boolean =
        try {
            val ref = reference.child(ThoughtDiaryEntity::class.simpleName!!)
            val key = ref.push().key

            ref.child(key!!).setValue(it)

            reference.child(THOUGHT_DIARIES_LIST).child(key).setValue(it.situation)

            true
        } catch (t: Throwable) {
            false
        }


    companion object {
        private const val THOUGHT_DIARIES_LIST = "thought diaries list"
    }
}
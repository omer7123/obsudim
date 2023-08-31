package com.example.mypsychologist.data.repository

import com.example.mypsychologist.di.AppModule
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.repository.CbtRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

    override suspend fun getThoughtDiariesFor(clientId: String): HashMap<String, String> =
        suspendCoroutine { continuation ->

            Firebase.database(AppModule.URL).reference
                .child(clientId).child(THOUGHT_DIARIES_LIST).get()
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

    override suspend fun getThoughtDiaryFor(clientId: String, id: String): ThoughtDiaryEntity =
        suspendCoroutine { continuation ->

            Firebase.database(AppModule.URL).reference.child(clientId)
                .child(ThoughtDiaryEntity::class.simpleName!!).child(id).get()
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

    override fun editAutoThought(diaryId: String, newText: String): Boolean =
        try {
            reference.child(ThoughtDiaryEntity::class.simpleName!!)
                .child(diaryId)
                .updateChildren(mapOf(AUTO_THOUGHT to newText))

            true
        } catch (t: Throwable) {
            false
        }

    override fun editAlternativeThought(diaryId: String, newText: String): Boolean =
        try {
            reference.child(ThoughtDiaryEntity::class.simpleName!!)
                .child(diaryId)
                .updateChildren(mapOf(ALTERNATIVE_THOUGHT to newText))

            true
        } catch (t: Throwable) {
            false
        }


    companion object {
        private const val THOUGHT_DIARIES_LIST = "thought diaries list"
        private const val AUTO_THOUGHT = "autoThought"
        private const val ALTERNATIVE_THOUGHT = "alternativeThought"
    }
}
package com.example.mypsychologist.data.repository

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.EducationTopicEntity
import com.example.mypsychologist.domain.repository.EducationRepository
import com.example.mypsychologist.pmap
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class EducationRepositoryImpl @Inject constructor(private val reference: DatabaseReference) :
    EducationRepository {
    override suspend fun getProgress(topic: String): Int =
        suspendCoroutine { continuation ->

            reference.child(EDUCATION).child(topic).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume((snapshot.value ?: 0).toString().toInt())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun getTopics(): List<EducationTopicEntity> =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            topics.pmap { it.copy(currentCard = getProgress(it.tag)) }
        }

    private val topics = listOf(
        EducationTopicEntity(R.string.psychology_base, 0, BASE),
        EducationTopicEntity(R.string.cbt_base, 0, CBT_BASE),
        EducationTopicEntity(R.string.rebt_base, 0, REBT_BASE)
    )

    companion object {
        const val EDUCATION = "education"
        const val BASE = "psychology_base"
        const val CBT_BASE = "cbt_base"
        const val REBT_BASE = "rebt_base"
    }
}
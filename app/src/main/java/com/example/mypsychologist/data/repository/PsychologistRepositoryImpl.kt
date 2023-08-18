package com.example.mypsychologist.data.repository

import com.example.mypsychologist.di.AppModule
import com.example.mypsychologist.domain.entity.PsychologistCard
import com.example.mypsychologist.domain.entity.PsychologistData
import com.example.mypsychologist.domain.entity.PsychologistInfo
import com.example.mypsychologist.domain.repository.PsychologistRepository
import com.example.mypsychologist.getTypedValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PsychologistRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : PsychologistRepository {

    override suspend fun getPsychologists(): HashMap<String, PsychologistCard> =
        suspendCoroutine { continuation ->

            Firebase.database(AppModule.URL).reference
                .child(PsychologistCard::class.simpleName!!).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(
                        snapshot.getTypedValue<HashMap<String, PsychologistCard>>()
                            ?: HashMap()
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun getPsychologist(id: String): PsychologistData =
        suspendCoroutine { continuation ->

            Firebase.database(AppModule.URL).reference
                .child(PsychologistInfo::class.simpleName!!)
                .child(id).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(
                        PsychologistData(
                            info = snapshot.getTypedValue<PsychologistInfo>()
                                ?: PsychologistInfo(),
                            documents = listOf()
                        )
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override fun sendRequestTo(psychologistId: String, text: String): Boolean =
        try {
            Firebase.database(AppModule.URL).reference
                .child(psychologistId)
                .child(REQUESTS)
                .child(auth.currentUser!!.uid)
                .setValue(text)

            true
        } catch (t: Throwable) {
            false
        }

    companion object {
        private const val REQUESTS = "requests"
    }
}
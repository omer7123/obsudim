package com.example.mypsychologist.data.repository

import com.example.mypsychologist.di.AppModule
import com.example.mypsychologist.domain.entity.*
import com.example.mypsychologist.domain.repository.PsychologistRepository
import com.example.mypsychologist.getTypedValue
import com.example.mypsychologist.pmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PsychologistRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val reference: DatabaseReference
) : PsychologistRepository {

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
                .child(REQUESTS)
                .child(psychologistId)
                .child(auth.currentUser!!.uid)
                .setValue(text)

            true
        } catch (t: Throwable) {
            false
        }

    override suspend fun getClientsRequests(): List<ClientRequestEntity> =
        getRequestsToPsychologist(getOwnPsychologistId()).pmap {
            ClientRequestEntity(getClientName(it.key), it.key, it.value)
        }


    private suspend fun getRequestsToPsychologist(psychologistId: String): Map<String, String> =
        suspendCoroutine { continuation ->
            Firebase.database(AppModule.URL).reference
                .child(REQUESTS).child(psychologistId).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(
                        snapshot.getTypedValue<HashMap<String, String>>() ?: HashMap()
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    private suspend fun getOwnPsychologistId(): String =
        suspendCoroutine { continuation ->
            reference.child(ProfileRepositoryImpl.OWN_PSYCHOLOGIST_ID).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.value.toString())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    private suspend fun getClientName(id: String): String =
        suspendCoroutine { continuation ->
            Firebase.database(AppModule.URL).reference
                .child(id).child(ProfileRepositoryImpl.NAME).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.value.toString())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun sendAnswerToRequest(accept: Boolean, clientId: String): Boolean =
        try {
            val ref = Firebase.database(AppModule.URL).reference
            val psychologistId = getOwnPsychologistId()

            if (accept) {
                val key = ref.child(psychologistId).child(CLIENTS).push().key!!
                ref.child(psychologistId).child(CLIENTS).child(key).setValue(clientId)
                ref.child(clientId).child(PSYCHOLOGIST).setValue(psychologistId)
            }

            ref.child(REQUESTS)
                .child(psychologistId)
                .child(auth.currentUser!!.uid)
                .removeValue()

            true
        } catch (t: Throwable) {
            false
        }

    override suspend fun getTasks(psychologistId: String): HashMap<String, TaskEntity> =
        suspendCoroutine { continuation ->
            reference.child(TASKS).child(psychologistId).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(
                        snapshot.getTypedValue<HashMap<String, TaskEntity>>() ?: hashMapOf()
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override fun markTaskAsCompleted(taskId: String, psychologistId: String) =
        try {
            reference.child(TASKS).child(psychologistId).child(taskId)
                .child(TaskEntity::completed.name).setValue(true)

            true
        } catch (t: Throwable) {
            false
        }

    override fun markTaskAsNotCompleted(taskId: String, psychologistId: String) =
        try {
            reference.child(TASKS).child(psychologistId).child(taskId)
                .child(TaskEntity::completed.name).setValue(false)

            true
        } catch (t: Throwable) {
            false
        }


    companion object {
        private const val REQUESTS = "requests"
        private const val PSYCHOLOGIST = "psychologist"
        const val CLIENTS = "clients"
        const val TASKS = "tasks"
    }
}
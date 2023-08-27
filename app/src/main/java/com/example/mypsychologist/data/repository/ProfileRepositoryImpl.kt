package com.example.mypsychologist.data.repository

import android.net.Uri
import com.example.mypsychologist.di.AppModule
import com.example.mypsychologist.domain.entity.ClientDataEntity
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.entity.PsychologistCard
import com.example.mypsychologist.domain.entity.PsychologistInfo
import com.example.mypsychologist.domain.repository.ProfileRepository
import com.example.mypsychologist.getTypedValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProfileRepositoryImpl @Inject constructor(
    private val reference: DatabaseReference,
    private val auth: FirebaseAuth
) :
    ProfileRepository {

    override suspend fun deleteAccount(): Boolean =
        suspendCoroutine { continuation ->
            auth.currentUser!!.delete().addOnCompleteListener { task ->
                continuation.resume(task.isSuccessful)
            }
        }

    override fun sendFeedback(text: String): Boolean =
        try {
            val ref = Firebase.database(AppModule.URL).reference.child(FEEDBACK)
            val key = ref.push().key!!

            ref.child(key).setValue(text)
            true
        } catch (t: Throwable) {
            false
        }

    override fun savePsychologist(info: PsychologistInfo): Boolean =
        try {
            val ref = Firebase.database(AppModule.URL).reference
            val key = ref.child(PsychologistInfo::class.simpleName!!).push().key!!

            ref.child(PsychologistInfo::class.simpleName!!).child(key).setValue(info)

            ref.child(PsychologistCard::class.simpleName!!).child(key)
                .setValue(PsychologistCard(info.name, info.specialization.joinToString(), 0))

            reference.child(OWN_PSYCHOLOGIST_ID).setValue(key)

            true
        } catch (t: Throwable) {
            false
        }

    override fun savePsychologist(docs: List<Uri>): Boolean =
        true

    override suspend fun getClientData(): ClientDataEntity =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            ClientDataEntity(
                getUserName(reference),
                getBirthday(reference),
                getGender(reference),
                getDiagnosis(reference),
                getRequest(reference),
                auth.currentUser!!.email ?: "",
                auth.currentUser!!.phoneNumber ?: ""
            )
        }

    private suspend fun getUserName(reference: DatabaseReference): String =
        suspendCoroutine { continuation ->
            reference.child(NAME).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.value.toString())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    private suspend fun getBirthday(reference: DatabaseReference): Long =
        suspendCoroutine { continuation ->
            reference.child(BIRTHDAY).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.getTypedValue<Long>() ?: 0)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    private suspend fun getGender(reference: DatabaseReference): String =
        suspendCoroutine { continuation ->
            reference.child(GENDER).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.value.toString())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    private suspend fun getDiagnosis(reference: DatabaseReference): String =
        suspendCoroutine { continuation ->
            reference.child(DIAGNOSIS).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.value.toString())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    private suspend fun getRequest(reference: DatabaseReference): List<String> =
        suspendCoroutine { continuation ->
            reference.child(REQUEST).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.getTypedValue<List<String>>() ?: listOf())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override fun changeName(it: String): Boolean =
        try {
            reference.child(NAME).setValue(it)
            true
        } catch (t: Throwable) {
            false
        }


    override fun changeBirthday(it: Long): Boolean =
        try {
            reference.child(BIRTHDAY).setValue(it)
            true
        } catch (t: Throwable) {
            false
        }

    override fun changeGender(it: String): Boolean =
        try {
            reference.child(GENDER).setValue(it)
            true
        } catch (t: Throwable) {
            false
        }

    override fun changeDiagnosis(it: String): Boolean =
        try {
            reference.child(DIAGNOSIS).setValue(it)
            true
        } catch (t: Throwable) {
            false
        }

    override fun changeRequest(it: List<String>): Boolean =
        try {
            reference.child(REQUEST).removeValue()
            reference.child(REQUEST).setValue(it)
            true
        } catch (t: Throwable) {
            false
        }

    override suspend fun changeMail(it: String): Boolean =
        suspendCoroutine { continuation ->
            auth.currentUser!!.updateEmail(it).addOnCompleteListener { task ->
                if (task.isSuccessful)
                    continuation.resume(true)
                else
                    continuation.resume(false)
            }.addOnFailureListener {
                continuation.resume(false)
            }
        }

    override suspend fun changePhone(it: String): Boolean =
        true


    override suspend fun changePassword(it: String): Boolean =
        suspendCoroutine { continuation ->
            auth.currentUser!!.updatePassword(it).addOnCompleteListener { task ->
                if (task.isSuccessful)
                    continuation.resume(true)
                else
                    continuation.resume(false)
            }.addOnFailureListener {
                continuation.resume(false)
            }
        }

    override suspend fun checkIfPsychologist(): Boolean =
        suspendCoroutine { continuation ->
            reference.child(OWN_PSYCHOLOGIST_ID).get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.toString().isNotEmpty())
                        continuation.resume(true)
                    else
                        continuation.resume(false)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun getClientInfo(clientId: String): ClientInfoEntity =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {

            val client = Firebase.database(AppModule.URL).reference.child(clientId)

            ClientInfoEntity(
                getUserName(client),
                getBirthday(client),
                getGender(client),
                getDiagnosis(client),
                getRequest(client)
            )
        }

    companion object {
        private const val FEEDBACK = "feedback"
        const val OWN_PSYCHOLOGIST_ID = "own_psychologist_id"
        const val NAME = "name"
        private const val BIRTHDAY = "birthday"
        private const val GENDER = "gender"
        private const val DIAGNOSIS = "diagnosis"
        private const val REQUEST = "request"
    }
}
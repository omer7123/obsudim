package com.example.mypsychologist.data.repository

import android.net.Uri
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.data.remote.profile.UserDataSource
import com.example.mypsychologist.di.AppModule
import com.example.mypsychologist.domain.entity.*
import com.example.mypsychologist.domain.repository.ProfileRepository
import com.example.mypsychologist.extensions.getTypedValue
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
    private val dataSource: UserDataSource,
    private val localDataSource: AuthenticationSharedPrefDataSource
) :
    ProfileRepository {

    override suspend fun saveClient(info: ClientInfoEntity): Resource<String> =
        dataSource.updateUser(info.toEntity(), Token(localDataSource.getToken()))



    override suspend fun deleteAccount(): Boolean =
        suspendCoroutine { continuation ->

        }

    override suspend fun sendFeedback(text: String): Boolean =
        try {
            val ref = Firebase.database(AppModule.URL).reference.child(FEEDBACK)
            val key = ref.push().key!!

            ref.child(key).setValue(text)
            true
        } catch (t: Throwable) {
            false
        }

    override suspend fun savePsychologist(info: PsychologistInfo): Boolean =
        try {
            true
        } catch (t: Throwable) {
            false
        }

    override suspend fun savePsychologist(docs: List<Uri>): Boolean =
        true

    override suspend fun changeRequest(it: List<String>): Boolean =
        try {

            true
        } catch (t: Throwable) {
            false
        }

    override suspend fun changeMail(it: String): Boolean =
        suspendCoroutine { continuation ->

        }

    override suspend fun changePhone(it: String): Boolean =
        true


    override suspend fun changePassword(it: String): Boolean =
        suspendCoroutine { continuation ->

        }

    override suspend fun checkIfPsychologist(): Boolean =
        suspendCoroutine { continuation ->

        }

    override suspend fun getClients(): List<ClientCardEntity> =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            listOf()
        }

    override suspend fun getClientInfo(clientId: String) = ClientInfoEntity() 


    private suspend fun getOwnPsychologistId(): String =
        suspendCoroutine { continuation ->

        }

    override suspend fun getClientTasks(clientId: String): HashMap<String, TaskEntity> =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            loadClientTasksFor(getOwnPsychologistId(), clientId)
        }

    private suspend fun loadClientTasksFor(
        psychologistId: String,
        clientId: String
    ): HashMap<String, TaskEntity> =
        suspendCoroutine { continuation ->

            Firebase.database(AppModule.URL).reference.child(clientId)
                .child(PsychologistRepositoryImpl.TASKS).child(psychologistId).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(
                        snapshot.getTypedValue<HashMap<String, TaskEntity>>() ?: hashMapOf()
                    )
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun newTask(task: String, clientId: String): String =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            ""
        }


    override suspend fun deleteTask(taskId: String, clientId: String) {
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            Firebase.database(AppModule.URL).reference.child(clientId)
                .child(PsychologistRepositoryImpl.TASKS).child(getOwnPsychologistId()).child(taskId)
                .removeValue()
        }
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
package com.example.mypsychologist.data.repository

import android.util.Log
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.remote.psychologist.PsychologistDataSource
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.MyPsychologistEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.TaskEntity
import com.example.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class PsychologistRepositoryImpl @Inject constructor(
    private val dataSource: PsychologistDataSource,
    private val localDataSource: AuthenticationSharedPrefDataSource
) : PsychologistRepository {

    override suspend fun getTasks(): Resource<List<TaskEntity>> {
        return when(val result = dataSource.getTasks()){
            is Resource.Error -> Resource.Error(result.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> {
                val listEntity = result.data.map { it.toEntity() }
                Resource.Success(listEntity)
            }
        }
    }

    override suspend fun getYourPsychologists(): Resource<List<MyPsychologistEntity>> {
        return when(val result = dataSource.getOwnPsychologists()){
            is Resource.Error -> Resource.Error(result.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data.map { it.toEntity() })
        }
    }

    //    override suspend fun getPsychologists(): HashMap<String, PsychologistCard> =
//        suspendCoroutine { continuation ->
//
//            Firebase.database(AppModule.URL).reference
//                .child(PsychologistCard::class.simpleName!!).get()
//                .addOnSuccessListener { snapshot ->
//                    continuation.resume(
//                        snapshot.getTypedValue<HashMap<String, PsychologistCard>>()
//                            ?: HashMap()
//                    )
//                }
//                .addOnFailureListener {
//                    continuation.resumeWithException(it)
//                }
//        }
//
//    override suspend fun getOwnPsychologists(): HashMap<String, PsychologistWithTaskCount> =
//        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
//            HashMap(
//                getOwnPsychologistIds().pmap { id ->
//
//                    val psychologistWithTaskCount =
//                        PsychologistWithTaskCount(
//                            getPsychologistCardBy(id),
//                            getPsychologistTaskCount(id)
//                        )
//
//                    Pair(id, psychologistWithTaskCount)
//                }.associateBy(
//                    { it.first }, { it.second }
//                )
//            )
//        }
//
//    private suspend fun getOwnPsychologistIds(): List<String> =
//        suspendCoroutine { continuation ->
//
//            reference.child(PSYCHOLOGIST).get()
//                .addOnSuccessListener { snapshot ->
//                    continuation.resume(
//                        snapshot.getTypedValue<HashMap<String, String>>()?.values?.toList()
//                            ?: listOf()
//                    )
//                }
//                .addOnFailureListener {
//                    continuation.resumeWithException(it)
//                }
//        }
//
//    private suspend fun getPsychologistCardBy(id: String): PsychologistCard =
//        suspendCoroutine { continuation ->
//
//            Firebase.database(AppModule.URL).reference
//                .child(PsychologistCard::class.simpleName!!).child(id).get()
//                .addOnSuccessListener { snapshot ->
//                    continuation.resume(
//                        snapshot.getTypedValue<PsychologistCard>()
//                            ?: PsychologistCard()
//                    )
//                }
//                .addOnFailureListener {
//                    continuation.resumeWithException(it)
//                }
//        }
//
//    private suspend fun getPsychologistTaskCount(psychologistId: String): Int =
//        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
//            getTasks(psychologistId).size
//        }
//
//    override suspend fun getPsychologist(id: String): PsychologistData =
//        suspendCoroutine { continuation ->
//
//            Firebase.database(AppModule.URL).reference
//                .child(PsychologistInfo::class.simpleName!!)
//                .child(id).get()
//                .addOnSuccessListener { snapshot ->
//                    continuation.resume(
//                        PsychologistData(
//                            info = snapshot.getTypedValue<PsychologistInfo>()
//                                ?: PsychologistInfo(),
//                            documents = listOf()
//                        )
//                    )
//                }
//                .addOnFailureListener {
//                    continuation.resumeWithException(it)
//                }
//        }
//
//    override fun sendRequestTo(psychologistId: String, text: String): Boolean =
//        try {
//            Firebase.database(AppModule.URL).reference
//                .child(REQUESTS)
//                .child(psychologistId)
//                .child(auth.currentUser!!.uid)
//                .setValue(text)
//
//            true
//        } catch (t: Throwable) {
//            false
//        }
//
//    override suspend fun getClientsRequests(): List<ClientRequestEntity> =
//        getRequestsToPsychologist(getOwnPsychologistId()).pmap {
//            ClientRequestEntity(getClientName(it.key), it.key, it.value)
//        }
//
//
//    private suspend fun getRequestsToPsychologist(psychologistId: String): Map<String, String> =
//        suspendCoroutine { continuation ->
//            Firebase.database(AppModule.URL).reference
//                .child(REQUESTS).child(psychologistId).get()
//                .addOnSuccessListener { snapshot ->
//                    continuation.resume(
//                        snapshot.getTypedValue<HashMap<String, String>>() ?: HashMap()
//                    )
//                }
//                .addOnFailureListener {
//                    continuation.resumeWithException(it)
//                }
//        }
//
//    private suspend fun getOwnPsychologistId(): String =
//        suspendCoroutine { continuation ->
//            reference.child(ProfileRepositoryImpl.OWN_PSYCHOLOGIST_ID).get()
//                .addOnSuccessListener { snapshot ->
//                    continuation.resume(snapshot.value.toString())
//                }
//                .addOnFailureListener {
//                    continuation.resumeWithException(it)
//                }
//        }
//
//    private suspend fun getClientName(id: String): String =
//        suspendCoroutine { continuation ->
//            Firebase.database(AppModule.URL).reference
//                .child(id).child(ProfileRepositoryImpl.NAME).get()
//                .addOnSuccessListener { snapshot ->
//                    continuation.resume(snapshot.value.toString())
//                }
//                .addOnFailureListener {
//                    continuation.resumeWithException(it)
//                }
//        }
//
//    override suspend fun sendAnswerToRequest(accept: Boolean, clientId: String): Boolean =
//        try {
//            val ref = Firebase.database(AppModule.URL).reference
//            val psychologistId = getOwnPsychologistId()
//
//            if (accept) {
//                saveClientOfPsychologist(ref, psychologistId, clientId)
//                savePsychologistOfClient(ref, psychologistId, clientId)
//            }
//
//            removeRequest(ref, psychologistId, clientId)
//
//            true
//        } catch (t: Throwable) {
//            false
//        }
//
//    private fun saveClientOfPsychologist(ref: DatabaseReference, psychologistId: String, clientId: String) {
//        val childKey = ref.child(psychologistId).child(CLIENTS).push().key!!
//        ref.child(psychologistId).child(CLIENTS).child(childKey).setValue(clientId)
//    }
//
//    private fun savePsychologistOfClient(ref: DatabaseReference, psychologistId: String, clientId: String) {
//        val psychologistKey = ref.child(clientId).child(PSYCHOLOGIST).push().key!!
//        ref.child(clientId).child(PSYCHOLOGIST).child(psychologistKey)
//            .setValue(psychologistId)
//    }
//
//    private fun removeRequest(ref: DatabaseReference, psychologistId: String, clientId: String) {
//        ref.child(REQUESTS)
//            .child(psychologistId)
//            .child(clientId)
//            .removeValue()
//    }
//
//    override suspend fun getTasks(psychologistId: String): HashMap<String, TaskEntity> =
//        suspendCoroutine { continuation ->
//            reference.child(TASKS).child(psychologistId).get()
//                .addOnSuccessListener { snapshot ->
//                    continuation.resume(
//                        snapshot.getTypedValue<HashMap<String, TaskEntity>>() ?: hashMapOf()
//                    )
//                }
//                .addOnFailureListener {
//                    continuation.resumeWithException(it)
//                }
//        }
//
    override suspend fun markTaskAsCompleted(taskId: String) =
        dataSource.markTaskAsCompleted(taskId)

    override suspend fun markTaskAsNotCompleted(taskId: String) =
        dataSource.markTaskAsUnfulfilled(taskId)

    override suspend fun getOwnPsychologists(): Resource<List<ManagerEntity>> {
        return when(val result = dataSource.getManagersList()){
            is Resource.Error -> {
                Log.d("Psychologists error", result.msg.toString())
                Resource.Error(result.msg.toString(), null)
            }
            Resource.Loading -> Resource.Loading
            is Resource.Success -> {
                val listEntity = result.data.map { it.toEntity() }
                Resource.Success(listEntity)
            }
        }
    }



    override suspend fun getStatusToRequestManager(): Boolean =
        localDataSource.getStatusRequestToManager()


    companion object {
        private const val REQUESTS = "requests"
        private const val PSYCHOLOGIST = "psychologist"
        const val CLIENTS = "clients"
        const val TASKS = "tasks"
    }
}
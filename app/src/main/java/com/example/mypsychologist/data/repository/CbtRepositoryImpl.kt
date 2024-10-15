package com.example.mypsychologist.data.repository

import android.util.Log
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toDiaryEntities
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.converters.toModel
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.remote.exercises.DiaryDataSource
import com.example.mypsychologist.di.AppModule
import com.example.mypsychologist.domain.entity.DiaryRecordEntity
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.repository.CbtRepository
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CbtRepositoryImpl @Inject constructor(
    private val diaryDataSource: DiaryDataSource,
    private val localDataSource: AuthenticationSharedPrefDataSource
) : CbtRepository {

    override suspend fun getThoughtDiaries(): Resource<List<DiaryRecordEntity>> =
        when(val result = diaryDataSource.getCBTDiary(localDataSource.getUserId())) {
            is Resource.Error -> {
                Log.d("Problem Error", result.msg.toString())
                Resource.Error(result.msg.toString(), null)
            }
            is Resource.Loading -> Resource.Loading
            is Resource.Success ->
                Resource.Success(result.data.toDiaryEntities())
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

    override suspend fun getThoughtDiary(id: String): Resource<ThoughtDiaryEntity> =
        when(val result = diaryDataSource.getCBTDiaryRecord(id)) {
            is Resource.Error -> {
                Log.d("Diary Error", result.msg.toString())
                Resource.Error(result.msg.toString(), null)
            }
            is Resource.Loading -> Resource.Loading
            is Resource.Success ->
                Resource.Success(result.data.toEntity())
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


    override suspend fun saveThoughtDiary(it: ThoughtDiaryEntity): Resource<String> =
        when(val result = diaryDataSource.save(it.toModel())) {
            is Resource.Error -> {
                Log.d("Diary Error", result.msg.toString())
                Resource.Error(result.msg.toString(), null)
            }
            is Resource.Loading -> Resource.Loading
            is Resource.Success ->
                Resource.Success(result.data.id)
        }

    override fun editAutoThought(diaryId: String, newText: String): Boolean =
        try {
            TODO()

            true
        } catch (t: Throwable) {
            false
        }

    override fun editAlternativeThought(diaryId: String, newText: String): Boolean =
        try {
            TODO()

            true
        } catch (t: Throwable) {
            false
        }


    companion object {
        private const val THOUGHT_DIARIES_LIST = "thought diaries list"
        private const val AUTO_THOUGHT = "autoThought"
        private const val ALTERNATIVE_THOUGHT = "alternativeThought"

        private const val FREE_DIARIES_LIST = "free diaries list"

    }
}
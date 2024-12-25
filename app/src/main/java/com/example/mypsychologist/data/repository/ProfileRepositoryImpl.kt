package com.example.mypsychologist.data.repository

import android.util.Log
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.converters.toModel
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.remote.profile.UserDataSource
import com.example.mypsychologist.di.AppModule
import com.example.mypsychologist.domain.entity.ClientInfoEntity
import com.example.mypsychologist.domain.entity.psychologistsEntity.SendRequestToPsychologistEntity
import com.example.mypsychologist.domain.repository.ProfileRepository
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
    private val localDataSource: AuthenticationSharedPrefDataSource
) :
    ProfileRepository {

    override suspend fun saveClient(info: ClientInfoEntity): Resource<String> = run {
        dataSource.updateUser(info.toModel())
    }

    override suspend fun getOwnInfo(): Resource<ClientInfoEntity> =
        when (val result = dataSource.getOwnData()) {
            is Resource.Error -> {
                Log.d("Info error", result.msg.toString())
                Resource.Error(result.msg.toString(), null)
            }
            is Resource.Loading -> Resource.Loading
            is Resource.Success ->
                Resource.Success(result.data.toEntity())                // поправить
        }

    override suspend fun sendRequestToPsychologist(sendRequestToPsychologistEntity: SendRequestToPsychologistEntity): Resource<String> {
        return when(val result = dataSource.sendRequestToManager(sendRequestToPsychologistEntity.toModel())){
            is Resource.Error -> Resource.Error(result.msg, null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> {
                localDataSource.saveStatusRequestToManager()
                Resource.Success(result.data)
            }
        }
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


    companion object {
        private const val FEEDBACK = "feedback"
        const val NAME = "name"
    }
}
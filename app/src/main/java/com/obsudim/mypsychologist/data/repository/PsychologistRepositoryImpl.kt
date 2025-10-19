package com.obsudim.mypsychologist.data.repository

import android.util.Log
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.converters.toEntity
import com.obsudim.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.obsudim.mypsychologist.data.remote.psychologist.PsychologistDataSource
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.MyPsychologistEntity
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.TaskEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class PsychologistRepositoryImpl @Inject constructor(
    private val dataSource: PsychologistDataSource,
    private val localDataSource: AuthenticationSharedPrefDataSource
) : PsychologistRepository {

    override suspend fun getTasks(): Resource<List<TaskEntity>> {
        return when (val result = dataSource.getTasks()) {
            is Resource.Error -> Resource.Error(result.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> {
                val listEntity = result.data.map { it.toEntity() }
                Resource.Success(listEntity)
            }
        }
    }

    override suspend fun getYourPsychologists(): Resource<List<MyPsychologistEntity>> {
        return when (val result = dataSource.getOwnPsychologists()) {
            is Resource.Error -> Resource.Error(result.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data.map { it.toEntity() })
        }
    }

    override suspend fun markTaskAsCompleted(taskId: String) =
        dataSource.markTaskAsCompleted(taskId)

    override suspend fun markTaskAsNotCompleted(taskId: String) =
        dataSource.markTaskAsUnfulfilled(taskId)

    override suspend fun getOwnPsychologists(): Resource<List<ManagerEntity>> {
        return when (val result = dataSource.getManagersList()) {
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


}
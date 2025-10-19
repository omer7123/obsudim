package com.obsudim.mypsychologist.domain.repository.retrofit

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.MyPsychologistEntity
import com.obsudim.mypsychologist.domain.entity.psychologistsEntity.TaskEntity

interface PsychologistRepository {

    suspend fun getTasks(): Resource<List<TaskEntity>>

    suspend fun getYourPsychologists(): Resource<List<MyPsychologistEntity>>
    suspend fun markTaskAsCompleted(taskId: String): Resource<String>
    suspend fun markTaskAsNotCompleted(taskId: String): Resource<String>

    suspend fun getOwnPsychologists(): Resource<List<ManagerEntity>>

    suspend fun getStatusToRequestManager(): Boolean
}
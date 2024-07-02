package com.example.mypsychologist.data.remote.psychologist

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.ManagerModel
import com.example.mypsychologist.data.model.TaskModel
import javax.inject.Inject

class PsychologistDataSourceImpl @Inject constructor(private val api: PsychologistService) :
    PsychologistDataSource, BaseDataSource() {
    override suspend fun getManagersList(): Resource<List<ManagerModel>> = getResult {
        api.getManagersList()
    }

    override suspend fun getManager(userId: String): Resource<ManagerModel> = getResult  {
        api.getManager(userId)
    }

    override suspend fun getTasks(): Resource<List<TaskModel>> = getResult {
        api.getTasks()
    }

    override suspend fun markTaskAsCompleted(id: String): Resource<String> = getResult {
        api.markTaskAsCompleted(id)
    }

    override suspend fun markTaskAsUnfulfilled(id: String): Resource<String> = getResult {
        api.markTaskAsUnfulfilled(id)
    }
}
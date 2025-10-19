package com.obsudim.mypsychologist.data.remote.psychologist

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.ManagerModel
import com.obsudim.mypsychologist.data.model.MyPsychologistModel
import com.obsudim.mypsychologist.data.model.TaskIdModel
import com.obsudim.mypsychologist.data.model.TaskModel
import javax.inject.Inject

class PsychologistDataSourceImpl @Inject constructor(private val api: PsychologistService) :
    PsychologistDataSource, BaseDataSource() {
    override suspend fun getManagersList(): Resource<List<ManagerModel>> = getResult {
        api.getManagersList()
    }

    override suspend fun getOwnPsychologists(): Resource<List<MyPsychologistModel>> = getResult {
        api.getYourPsychologist()
    }

    override suspend fun getManager(userId: String): Resource<ManagerModel> = getResult {
        api.getManager(userId)
    }

    override suspend fun getTasks(): Resource<List<TaskModel>> = getResult {
        api.getTasks()
    }

    override suspend fun markTaskAsCompleted(id: String): Resource<String> = getResult {
        api.markTaskAsCompleted(TaskIdModel(id))
    }

    override suspend fun markTaskAsUnfulfilled(id: String): Resource<String> = getResult {
        api.markTaskAsUnfulfilled(TaskIdModel(id))
    }
}
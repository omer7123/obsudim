package com.example.mypsychologist.data.remote.psychologist

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.ManagerModel
import com.example.mypsychologist.data.model.MyPsychologistModel
import com.example.mypsychologist.data.model.TaskModel

interface PsychologistDataSource {
    suspend fun getManagersList(): Resource<List<ManagerModel>>
    suspend fun getOwnPsychologists(): Resource<List<MyPsychologistModel>>
    suspend fun getManager(userId: String): Resource<ManagerModel>
    suspend fun getTasks(): Resource<List<TaskModel>>
    suspend fun markTaskAsCompleted(id: String): Resource<String>
    suspend fun markTaskAsUnfulfilled(id: String): Resource<String>
}
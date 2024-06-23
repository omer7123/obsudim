package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.BeliefCheckModel
import javax.inject.Inject

class BeliefsDataSourceImpl @Inject constructor(private val api: BeliefService) : BeliefsDataSource, BaseDataSource() {
    override suspend fun save(beliefCheck: BeliefCheckModel): Resource<String> = getResult {
        api.saveBeliefCheck(beliefCheck)
    }

    override suspend fun getBeliefCheck(id: String): Resource<BeliefCheckModel> = getResult {
        api.getBeliefCheck(id)
    }
}
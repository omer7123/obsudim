package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.BeliefCheckModel

interface BeliefsDataSource {
    suspend fun save(beliefCheck: BeliefCheckModel): Resource<String>
    suspend fun getBeliefCheck(id: String): Resource<BeliefCheckModel>
}
package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.ProblemModel

interface ProblemDataSource {
    suspend fun save(problem: ProblemModel): Resource<String>
}
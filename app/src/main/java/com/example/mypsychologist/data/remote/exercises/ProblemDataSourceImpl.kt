package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.ProblemModel
import javax.inject.Inject

class ProblemDataSourceImpl @Inject constructor(private val api: ProblemService) : ProblemDataSource, BaseDataSource() {
    override suspend fun save(problem: ProblemModel): Resource<String> = getResult {
        api.saveNewProblem(problem)
    }

    override suspend fun getProblems(userId: String): Resource<List<ProblemModel>> = getResult {
        api.getProblems(userId)
    }

}
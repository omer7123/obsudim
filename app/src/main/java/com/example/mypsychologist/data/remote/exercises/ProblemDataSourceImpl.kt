package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.GetProblemModel
import com.example.mypsychologist.data.model.SaveProblemModel
import javax.inject.Inject

class ProblemDataSourceImpl @Inject constructor(private val api: ProblemService) : ProblemDataSource, BaseDataSource() {
    override suspend fun save(problem: SaveProblemModel): Resource<String> = getResult {
        api.saveNewProblem(problem)
    }

    override suspend fun getProblems(userId: String): Resource<List<GetProblemModel>> = getResult {
        api.getProblems(userId)
    }

}
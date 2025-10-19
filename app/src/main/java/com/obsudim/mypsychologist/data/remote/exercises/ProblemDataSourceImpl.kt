package com.obsudim.mypsychologist.data.remote.exercises

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.GetProblemModel
import com.obsudim.mypsychologist.data.model.ProblemAnalysisModel
import com.obsudim.mypsychologist.data.model.SaveProblemModel
import javax.inject.Inject

class ProblemDataSourceImpl @Inject constructor(private val api: ProblemService) :
    ProblemDataSource, BaseDataSource() {
    override suspend fun save(problem: SaveProblemModel): Resource<String> = getResult {
        api.saveNewProblem(problem)
    }

    override suspend fun getProblems(userId: String): Resource<List<GetProblemModel>> = getResult {
        api.getProblems(userId)
    }

    override suspend fun saveProblemAnalysisItem(model: ProblemAnalysisModel): Resource<String> =
        getResult {
            api.saveProblemAnalysisItem(model)
        }

    override suspend fun getProblemAnalysis(problemId: String): Resource<List<ProblemAnalysisModel>> =
        getResult {
            api.getProblemAnalysis(problemId)
        }

}
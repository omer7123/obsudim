package com.obsudim.mypsychologist.data.remote.exercises

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.GetProblemModel
import com.obsudim.mypsychologist.data.model.ProblemAnalysisModel
import com.obsudim.mypsychologist.data.model.SaveProblemModel

interface ProblemDataSource {
    suspend fun save(problem: SaveProblemModel): Resource<String>

    suspend fun getProblems(userId: String): Resource<List<GetProblemModel>>

    suspend fun saveProblemAnalysisItem(model: ProblemAnalysisModel): Resource<String>

    suspend fun getProblemAnalysis(problemId: String): Resource<List<ProblemAnalysisModel>>
}
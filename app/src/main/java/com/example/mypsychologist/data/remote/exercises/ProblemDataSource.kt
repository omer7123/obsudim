package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.GetProblemModel
import com.example.mypsychologist.data.model.ProblemAnalysisModel
import com.example.mypsychologist.data.model.SaveProblemModel

interface ProblemDataSource {
    suspend fun save(problem: SaveProblemModel): Resource<String>

    suspend fun getProblems(userId: String): Resource<List<GetProblemModel>>

    suspend fun saveProblemAnalysisItem(model: ProblemAnalysisModel): Resource<String>

    suspend fun getProblemAnalysis(problemId: String): Resource<List<ProblemAnalysisModel>>
}
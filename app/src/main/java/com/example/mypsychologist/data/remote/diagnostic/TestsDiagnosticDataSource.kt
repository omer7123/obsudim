package com.example.mypsychologist.data.remote.diagnostic

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.ConclusionOfTestModel
import com.example.mypsychologist.data.model.QuestionOfTestModel
import com.example.mypsychologist.data.model.SaveTestResultModel
import com.example.mypsychologist.data.model.TestInfoModel
import com.example.mypsychologist.data.model.TestModel
import com.example.mypsychologist.data.model.TestResultsGetModel

interface TestsDiagnosticDataSource{

    suspend fun getAllTests(): Resource<List<TestModel>>
    suspend fun saveTestResult(testResultModel: SaveTestResultModel): Resource<List<ConclusionOfTestModel>>
    suspend fun getTestResults(testId: String): Resource<List<TestResultsGetModel>>
    suspend fun getInfoAboutTest(testId: String): Resource<TestInfoModel>
    suspend fun getQuestionsOfTest(testId: String): Resource<List<QuestionOfTestModel>>
}
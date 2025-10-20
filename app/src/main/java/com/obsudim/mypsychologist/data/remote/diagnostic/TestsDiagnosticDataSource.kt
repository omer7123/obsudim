package com.obsudim.mypsychologist.data.remote.diagnostic

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.QuestionOfTestModel
import com.obsudim.mypsychologist.data.model.ResultAfterSaveModel
import com.obsudim.mypsychologist.data.model.SaveTestResultModel
import com.obsudim.mypsychologist.data.model.TestInfoModel
import com.obsudim.mypsychologist.data.model.TestModel
import com.obsudim.mypsychologist.data.model.TestResultsGetModel

interface TestsDiagnosticDataSource{

    suspend fun getAllTests(): Resource<List<TestModel>>
    suspend fun saveTestResult(testResultModel: SaveTestResultModel): Resource<ResultAfterSaveModel>
    suspend fun getTestResults(testId: String): Resource<List<TestResultsGetModel>>
    suspend fun getInfoAboutTest(testId: String): Resource<TestInfoModel>
    suspend fun getQuestionsOfTest(testId: String): Resource<List<QuestionOfTestModel>>

    suspend fun getTestResult(testResultId: String): Resource<TestResultsGetModel>
}
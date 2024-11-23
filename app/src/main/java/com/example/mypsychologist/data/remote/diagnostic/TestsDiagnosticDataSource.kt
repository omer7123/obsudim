package com.example.mypsychologist.data.remote.diagnostic

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.ResultAfterSaveModel
import com.example.mypsychologist.data.model.SaveTestResultModel
import com.example.mypsychologist.data.model.TestInfoForPassingModel
import com.example.mypsychologist.data.model.TestInfoModel
import com.example.mypsychologist.data.model.TestModel
import com.example.mypsychologist.data.model.TestResultsGetModel

interface TestsDiagnosticDataSource{

    suspend fun getAllTests(): Resource<List<TestModel>>
    suspend fun saveTestResult(testResultModel: SaveTestResultModel): Resource<ResultAfterSaveModel>
    suspend fun getTestResults(testId: String): Resource<List<TestResultsGetModel>>
    suspend fun getInfoAboutTest(testId: String): Resource<TestInfoModel>
    suspend fun getQuestionsOfTest(testId: String): Resource<TestInfoForPassingModel>

    suspend fun getTestResult(testResultId: String): Resource<TestResultsGetModel>
}
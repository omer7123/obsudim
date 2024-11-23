package com.example.mypsychologist.data.remote.diagnostic

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.local.sharedPref.AuthenticationSharedPrefDataSource
import com.example.mypsychologist.data.model.ResultAfterSaveModel
import com.example.mypsychologist.data.model.SaveTestResultModel
import com.example.mypsychologist.data.model.TestInfoForPassingModel
import com.example.mypsychologist.data.model.TestInfoModel
import com.example.mypsychologist.data.model.TestModel
import com.example.mypsychologist.data.model.TestResultsGetModel
import javax.inject.Inject

class TestsDiagnosticDataSourceImpl @Inject constructor(
    private val api: TestsDiagnosticService,
    private val userDataSource: AuthenticationSharedPrefDataSource
) :
    TestsDiagnosticDataSource, BaseDataSource() {
    override suspend fun getAllTests(): Resource<List<TestModel>> = getResult {
        api.getAllTests()
    }

    override suspend fun saveTestResult(testResultModel: SaveTestResultModel): Resource<ResultAfterSaveModel> =
        getResult {
            api.saveTestResult(testResultModel)
        }

    override suspend fun getTestResults(testId: String): Resource<List<TestResultsGetModel>> {
        val userID = userDataSource.getUserId()
        return getResult { api.getTestResults(testId, userID) }
    }

    override suspend fun getInfoAboutTest(testId: String): Resource<TestInfoModel> = getResult {
        api.getTestInfo(testId)
    }

    override suspend fun getQuestionsOfTest(testId: String): Resource<TestInfoForPassingModel> =
        getResult {
            api.getQuestionsOfTest(testId)
        }

    override suspend fun getTestResult(testResultId: String): Resource<TestResultsGetModel> =
        getResult {
            api.getTestResult(testResultId)
        }
}
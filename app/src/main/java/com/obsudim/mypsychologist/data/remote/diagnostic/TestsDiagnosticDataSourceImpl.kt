package com.obsudim.mypsychologist.data.remote.diagnostic

import com.obsudim.mypsychologist.core.BaseDataSource
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.QuestionOfTestModel
import com.obsudim.mypsychologist.data.model.ResultAfterSaveModel
import com.obsudim.mypsychologist.data.model.SaveTestResultModel
import com.obsudim.mypsychologist.data.model.TestInfoModel
import com.obsudim.mypsychologist.data.model.TestModel
import com.obsudim.mypsychologist.data.model.TestResultsGetModel
import javax.inject.Inject

class TestsDiagnosticDataSourceImpl @Inject constructor(
    private val api: TestsDiagnosticService,
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
        return getResult { api.getTestResults(testId) }
    }

    override suspend fun getInfoAboutTest(testId: String): Resource<TestInfoModel> = getResult {
        api.getTestInfo(testId)
    }

    override suspend fun getQuestionsOfTest(testId: String): Resource<List<QuestionOfTestModel>> =
        getResult {
            api.getQuestionsOfTest(testId)
        }

    override suspend fun getTestResult(testResultId: String): Resource<TestResultsGetModel> =
        getResult {
            api.getTestResult(testResultId)
        }
}
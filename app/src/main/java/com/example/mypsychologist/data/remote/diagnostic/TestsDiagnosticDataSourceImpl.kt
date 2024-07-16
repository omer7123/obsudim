package com.example.mypsychologist.data.remote.diagnostic

import com.example.mypsychologist.core.BaseDataSource
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.TestModel
import javax.inject.Inject

class TestsDiagnosticDataSourceImpl @Inject constructor(private val api: TestsDiagnosticService): TestsDiagnosticDataSource, BaseDataSource() {
    override suspend fun getAllTests(): Resource<List<TestModel>> = getResult {
        api.getAllTests()
    }
}
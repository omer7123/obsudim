package com.example.mypsychologist.data.remote.diagnostic

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.SaveTestResultModel
import com.example.mypsychologist.data.model.TestModel

interface TestsDiagnosticDataSource{

    suspend fun getAllTests(): Resource<List<TestModel>>
    suspend fun saveTestResult(testResultModel: SaveTestResultModel): Resource<String>
}
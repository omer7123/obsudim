package com.example.mypsychologist.data.repository

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestResultEntity
import com.example.mypsychologist.domain.repository.DiagnosticRepository
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class DiagnosticsRepositoryImpl @Inject constructor(private val reference: DatabaseReference) :
    DiagnosticRepository {
    override fun saveBeckDepression(result: TestResultEntity): Boolean {
        return true
    }

    override fun getTestResults(title: String): List<TestResultEntity> {
        return listOf(
            TestResultEntity(3, R.string.no_depression, 4385302443L),
            TestResultEntity(5, R.string.no_depression, 4385402443L),
        )
    }
}
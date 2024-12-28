package com.example.mypsychologist.domain.useCase.diagnosticsUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.example.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class GetAllTestsUseCase @Inject constructor(private val repository: TestsDiagnosticRepository) {

    suspend operator fun invoke(): Resource<List<TestEntity>> = repository.getAllTests()
}
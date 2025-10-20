package com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.TestsDiagnosticRepository
import javax.inject.Inject

class GetAllTestsUseCase @Inject constructor(private val repository: TestsDiagnosticRepository) {

    suspend operator fun invoke(): Resource<List<TestEntity>> = repository.getAllTests()
}
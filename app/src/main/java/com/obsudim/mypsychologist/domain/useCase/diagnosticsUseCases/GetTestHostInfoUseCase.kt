package com.obsudim.mypsychologist.domain.useCase.diagnosticsUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diagnosticEntity.TestHostEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetTestHostInfoUseCase @Inject constructor(
    private val getTestInfoUseCase: GetTestInfoUseCase,
    private val getTestResultsUseCase: GetTestResultsUseCase,
) {
    suspend operator fun invoke(testId: String): Flow<Resource<TestHostEntity>> =
        combine(
            getTestInfoUseCase(testId),
            getTestResultsUseCase(testId)
        ) { testInfo, resultsTest ->
            when {
                testInfo is Resource.Loading || resultsTest is Resource.Loading -> {
                    Resource.Loading
                }

                testInfo is Resource.Error -> {
                    Resource.Error(testInfo.msg.toString(), null)
                }

                resultsTest is Resource.Error -> {
                    Resource.Error(resultsTest.toString(), null)
                }

                testInfo is Resource.Success && resultsTest is Resource.Success -> {
                    Resource.Success(
                        data = TestHostEntity(
                            testId = testInfo.data.testId,
                            title = testInfo.data.title,
                            description = testInfo.data.description,
                            shortDesc = testInfo.data.shortDesc,
                            history = resultsTest.data
                        )
                    )
                }

                else -> {
                    Resource.Loading
                }
            }
        }


}
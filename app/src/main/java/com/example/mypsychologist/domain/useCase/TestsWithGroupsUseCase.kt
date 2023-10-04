package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestCardEntity
import com.example.mypsychologist.domain.entity.TestGroupEntity
import javax.inject.Inject

class TestsWithGroupsUseCase @Inject constructor() {
    operator fun invoke(): Map<TestGroupEntity, List<TestCardEntity>> =
        mapOf(
            TestGroupEntity(R.string.depression, R.drawable.ic_sentiment_very_dissatisfied) to listOf(
                TestCardEntity(
                    R.string.depression_beck_test,
                    R.string.depression_beck_test_desc_short,
                    R.string.depression_beck_test_desc
                )
            )
        )
}
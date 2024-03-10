package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestCardEntity
import com.example.mypsychologist.domain.entity.TestGroupEntity
import javax.inject.Inject

class TestsWithGroupsUseCase @Inject constructor() {
    operator fun invoke(): Map<TestGroupEntity, List<TestCardEntity>> =
        mapOf(
            TestGroupEntity(R.string.universal, R.drawable.ic_info) to listOf(
                TestCardEntity(
                    R.string.dass21,
                    R.string.dass21_desc_short,
                    R.string.dass21_desc
                ),
                TestCardEntity(
                    R.string.jas,
                    R.string.jas_desc_short,
                    R.string.jas_desc
                ),
            ),
            TestGroupEntity(
                R.string.depression,
                R.drawable.ic_sentiment_very_dissatisfied
            ) to listOf(
                TestCardEntity(
                    R.string.depression_beck_test,
                    R.string.depression_beck_test_desc_short,
                    R.string.depression_beck_test_desc
                )
            ),
            TestGroupEntity(R.string.anxiety, R.drawable.ic_anxiety) to listOf(
                TestCardEntity(
                    R.string.cmq,
                    R.string.cmq_desc_short,
                    R.string.cmq_desc
                ),
                TestCardEntity(
                    R.string.stai,
                    R.string.stai_desc_short,
                    R.string.stai_desc
                )
            )
        )
}
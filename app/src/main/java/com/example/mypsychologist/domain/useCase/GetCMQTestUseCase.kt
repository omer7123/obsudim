package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestAnswerVariantEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity
import javax.inject.Inject

class GetCMQTestUseCase @Inject constructor() {
    operator fun invoke(): List<TestQuestionEntity> =
        listOf(
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_1
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_2
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_3
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_4
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_5
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_6
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_7
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_8
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_9
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_10
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_11
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_12
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_13
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_14
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_15
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_16
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_17
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_18
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_19
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_20
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_21
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_22
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_23
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_24
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_25
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_26
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 4),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 2),
                    TestAnswerVariantEntity(R.string.always, 1),
                ),
                R.string.smq_27
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_28
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_29
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_30
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_31
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_32
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_33
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_34
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_35
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_36
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_37
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_38
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_39
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_40
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 4),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 2),
                    TestAnswerVariantEntity(R.string.always, 1),
                ),
                R.string.smq_41
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_42
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_43
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_44
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.smq_45
            ),
        )
    companion object {
        const val TEST_NAME = "CMQ test"
    }
}
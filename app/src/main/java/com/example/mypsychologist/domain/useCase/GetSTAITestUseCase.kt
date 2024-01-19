package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestAnswerVariantEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity
import javax.inject.Inject

class GetSTAITestUseCase @Inject constructor() {
    operator fun invoke(): List<TestQuestionEntity> =
        listOf(
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 4),
                    TestAnswerVariantEntity(R.string.perhaps_so, 3),
                    TestAnswerVariantEntity(R.string.right, 2),
                    TestAnswerVariantEntity(R.string.quite_true, 1)
                ),
                R.string.stai_1
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 4),
                    TestAnswerVariantEntity(R.string.perhaps_so, 3),
                    TestAnswerVariantEntity(R.string.right, 2),
                    TestAnswerVariantEntity(R.string.quite_true, 1)
                ),
                R.string.stai_2
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 1),
                    TestAnswerVariantEntity(R.string.perhaps_so, 2),
                    TestAnswerVariantEntity(R.string.right, 3),
                    TestAnswerVariantEntity(R.string.quite_true, 4)
                ),
                R.string.stai_3
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 1),
                    TestAnswerVariantEntity(R.string.perhaps_so, 2),
                    TestAnswerVariantEntity(R.string.right, 3),
                    TestAnswerVariantEntity(R.string.quite_true, 4)
                ),
                R.string.stai_4
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 4),
                    TestAnswerVariantEntity(R.string.perhaps_so, 3),
                    TestAnswerVariantEntity(R.string.right, 2),
                    TestAnswerVariantEntity(R.string.quite_true, 1)
                ),
                R.string.stai_5
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 1),
                    TestAnswerVariantEntity(R.string.perhaps_so, 2),
                    TestAnswerVariantEntity(R.string.right, 3),
                    TestAnswerVariantEntity(R.string.quite_true, 4)
                ),
                R.string.stai_6
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 1),
                    TestAnswerVariantEntity(R.string.perhaps_so, 2),
                    TestAnswerVariantEntity(R.string.right, 3),
                    TestAnswerVariantEntity(R.string.quite_true, 4)
                ),
                R.string.stai_7
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 4),
                    TestAnswerVariantEntity(R.string.perhaps_so, 3),
                    TestAnswerVariantEntity(R.string.right, 2),
                    TestAnswerVariantEntity(R.string.quite_true, 1)
                ),
                R.string.stai_8
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 1),
                    TestAnswerVariantEntity(R.string.perhaps_so, 2),
                    TestAnswerVariantEntity(R.string.right, 3),
                    TestAnswerVariantEntity(R.string.quite_true, 4)
                ),
                R.string.stai_9
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 4),
                    TestAnswerVariantEntity(R.string.perhaps_so, 3),
                    TestAnswerVariantEntity(R.string.right, 2),
                    TestAnswerVariantEntity(R.string.quite_true, 1)
                ),
                R.string.stai_10
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 4),
                    TestAnswerVariantEntity(R.string.perhaps_so, 3),
                    TestAnswerVariantEntity(R.string.right, 2),
                    TestAnswerVariantEntity(R.string.quite_true, 1)
                ),
                R.string.stai_11
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 1),
                    TestAnswerVariantEntity(R.string.perhaps_so, 2),
                    TestAnswerVariantEntity(R.string.right, 3),
                    TestAnswerVariantEntity(R.string.quite_true, 4)
                ),
                R.string.stai_12
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 1),
                    TestAnswerVariantEntity(R.string.perhaps_so, 2),
                    TestAnswerVariantEntity(R.string.right, 3),
                    TestAnswerVariantEntity(R.string.quite_true, 4)
                ),
                R.string.stai_13
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 1),
                    TestAnswerVariantEntity(R.string.perhaps_so, 2),
                    TestAnswerVariantEntity(R.string.right, 3),
                    TestAnswerVariantEntity(R.string.quite_true, 4)
                ),
                R.string.stai_14
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 4),
                    TestAnswerVariantEntity(R.string.perhaps_so, 3),
                    TestAnswerVariantEntity(R.string.right, 2),
                    TestAnswerVariantEntity(R.string.quite_true, 1)
                ),
                R.string.stai_15
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 4),
                    TestAnswerVariantEntity(R.string.perhaps_so, 3),
                    TestAnswerVariantEntity(R.string.right, 2),
                    TestAnswerVariantEntity(R.string.quite_true, 1)
                ),
                R.string.stai_16
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 1),
                    TestAnswerVariantEntity(R.string.perhaps_so, 2),
                    TestAnswerVariantEntity(R.string.right, 3),
                    TestAnswerVariantEntity(R.string.quite_true, 4)
                ),
                R.string.stai_17
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 1),
                    TestAnswerVariantEntity(R.string.perhaps_so, 2),
                    TestAnswerVariantEntity(R.string.right, 3),
                    TestAnswerVariantEntity(R.string.quite_true, 4)
                ),
                R.string.stai_18
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 4),
                    TestAnswerVariantEntity(R.string.perhaps_so, 3),
                    TestAnswerVariantEntity(R.string.right, 2),
                    TestAnswerVariantEntity(R.string.quite_true, 1)
                ),
                R.string.stai_19
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_it_is_not, 4),
                    TestAnswerVariantEntity(R.string.perhaps_so, 3),
                    TestAnswerVariantEntity(R.string.right, 2),
                    TestAnswerVariantEntity(R.string.quite_true, 1)
                ),
                R.string.stai_20
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 4),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 2),
                    TestAnswerVariantEntity(R.string.always, 1),
                ),
                R.string.stai_21
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_22
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_23
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_24
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_25
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 4),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 2),
                    TestAnswerVariantEntity(R.string.always, 1),
                ),
                R.string.stai_26
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 4),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 2),
                    TestAnswerVariantEntity(R.string.always, 1),
                ),
                R.string.stai_27
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_28
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_29
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 4),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 2),
                    TestAnswerVariantEntity(R.string.always, 1),
                ),
                R.string.stai_30
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_31
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_32
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_33
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_34
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_35
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 4),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 2),
                    TestAnswerVariantEntity(R.string.always, 1),
                ),
                R.string.stai_36
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_37
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_38
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 4),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 2),
                    TestAnswerVariantEntity(R.string.always, 1),
                ),
                R.string.stai_39
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 1),
                    TestAnswerVariantEntity(R.string.sometimes, 2),
                    TestAnswerVariantEntity(R.string.often, 3),
                    TestAnswerVariantEntity(R.string.always, 4),
                ),
                R.string.stai_40
            )
        )

    companion object {
        const val TEST_NAME = "STAI test"
    }
}
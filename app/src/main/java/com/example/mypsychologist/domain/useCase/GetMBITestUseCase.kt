package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestAnswerVariantEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity
import javax.inject.Inject

class GetMBITestUseCase @Inject constructor() {
    operator fun invoke(): List<TestQuestionEntity> =
        listOf(
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_1
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_2
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_3
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_4
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_5
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_6
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_7
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_8
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_9
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_10
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_11
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_12
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_13
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_14
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_15
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_16
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_17
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_18
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_19
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_20
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_21
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.never, 0),
                    TestAnswerVariantEntity(R.string.very_rarely, 1),
                    TestAnswerVariantEntity(R.string.rarely, 2),
                    TestAnswerVariantEntity(R.string.sometimes, 3),
                    TestAnswerVariantEntity(R.string.often, 4),
                    TestAnswerVariantEntity(R.string.very_often, 5),
                    TestAnswerVariantEntity(R.string.daily, 6)
                ),
                R.string.mbi_22
            ),
            )

    companion object {
        const val TEST_NAME = "MBI test"
    }

}

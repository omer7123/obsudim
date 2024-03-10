package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestAnswerVariantEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity
import javax.inject.Inject

class GetJASTestUseCase @Inject constructor() {
    operator fun invoke(): List<TestQuestionEntity> =
        listOf(
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                    TestAnswerVariantEntity(R.string.rather_disagree, 2),
                    TestAnswerVariantEntity(R.string.something_in_between, 3),
                    TestAnswerVariantEntity(R.string.rather_agree, 4),
                    TestAnswerVariantEntity(R.string.agree, 5)
                ),
                R.string.jas_1
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                    TestAnswerVariantEntity(R.string.rather_disagree, 2),
                    TestAnswerVariantEntity(R.string.something_in_between, 3),
                    TestAnswerVariantEntity(R.string.rather_agree, 4),
                    TestAnswerVariantEntity(R.string.agree, 5)
                ),
                R.string.jas_2
            ),TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                    TestAnswerVariantEntity(R.string.rather_disagree, 2),
                    TestAnswerVariantEntity(R.string.something_in_between, 3),
                    TestAnswerVariantEntity(R.string.rather_agree, 4),
                    TestAnswerVariantEntity(R.string.agree, 5)
                ),
                R.string.jas_3
            ),TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                    TestAnswerVariantEntity(R.string.rather_disagree, 2),
                    TestAnswerVariantEntity(R.string.something_in_between, 3),
                    TestAnswerVariantEntity(R.string.rather_agree, 4),
                    TestAnswerVariantEntity(R.string.agree, 5)
                ),
                R.string.jas_4
            ),TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                    TestAnswerVariantEntity(R.string.rather_disagree, 2),
                    TestAnswerVariantEntity(R.string.something_in_between, 3),
                    TestAnswerVariantEntity(R.string.rather_agree, 4),
                    TestAnswerVariantEntity(R.string.agree, 5)
                ),
                R.string.jas_5
            ),TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                    TestAnswerVariantEntity(R.string.rather_disagree, 2),
                    TestAnswerVariantEntity(R.string.something_in_between, 3),
                    TestAnswerVariantEntity(R.string.rather_agree, 4),
                    TestAnswerVariantEntity(R.string.agree, 5)
                ),
                R.string.jas_6
            ),TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                    TestAnswerVariantEntity(R.string.rather_disagree, 2),
                    TestAnswerVariantEntity(R.string.something_in_between, 3),
                    TestAnswerVariantEntity(R.string.rather_agree, 4),
                    TestAnswerVariantEntity(R.string.agree, 5)
                ),
                R.string.jas_7
            ),TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                    TestAnswerVariantEntity(R.string.rather_disagree, 2),
                    TestAnswerVariantEntity(R.string.something_in_between, 3),
                    TestAnswerVariantEntity(R.string.rather_agree, 4),
                    TestAnswerVariantEntity(R.string.agree, 5)
                ),
                R.string.jas_8
            ),TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                    TestAnswerVariantEntity(R.string.rather_disagree, 2),
                    TestAnswerVariantEntity(R.string.something_in_between, 3),
                    TestAnswerVariantEntity(R.string.rather_agree, 4),
                    TestAnswerVariantEntity(R.string.agree, 5)
                ),
                R.string.jas_9
            ),TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                    TestAnswerVariantEntity(R.string.rather_disagree, 2),
                    TestAnswerVariantEntity(R.string.something_in_between, 3),
                    TestAnswerVariantEntity(R.string.rather_agree, 4),
                    TestAnswerVariantEntity(R.string.agree, 5)
                ),
                R.string.jas_10
            ),
        )
    companion object {
        const val TEST_NAME = "JAS test"
    }
}

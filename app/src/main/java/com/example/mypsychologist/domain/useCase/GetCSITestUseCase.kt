package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestAnswerVariantEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity
import javax.inject.Inject

class GetCSITestUseCase @Inject constructor() {

    operator fun invoke(): List<TestQuestionEntity> =
        listOf(
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_1
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_2
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_3
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_4
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_5
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_6
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_7
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_8
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_9
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_10
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_11
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_12
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_13
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_14
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_15
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_16
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_17
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_18
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_19
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_20
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_21
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_22
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_23
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_24
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_25
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_26
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_27
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_28
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_29
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_30
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_31
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_32
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.completely_agree, 3),
                    TestAnswerVariantEntity(R.string.agree, 2),
                    TestAnswerVariantEntity(R.string.do_not_agree, 1),
                ),
                R.string.csi_33
            ),
        )
    companion object {
        const val TEST_NAME = "MBI test"
    }
}
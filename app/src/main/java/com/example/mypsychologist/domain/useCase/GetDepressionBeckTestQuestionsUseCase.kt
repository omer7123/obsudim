package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestAnswerVariantEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity
import javax.inject.Inject

class GetDepressionBeckTestQuestionsUseCase @Inject constructor() {
    operator fun invoke(): List<TestQuestionEntity> =
        listOf(
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.not_sad, 0),
                    TestAnswerVariantEntity(R.string.sad, 1),
                    TestAnswerVariantEntity(R.string.always_sad, 2),
                    TestAnswerVariantEntity(R.string.strong_sad, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.future_good, 0),
                    TestAnswerVariantEntity(R.string.future_not_good, 1),
                    TestAnswerVariantEntity(R.string.future_bad, 2),
                    TestAnswerVariantEntity(R.string.future_terrible, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.not_looser, 0),
                    TestAnswerVariantEntity(R.string.confuse_more_often, 1),
                    TestAnswerVariantEntity(R.string.only_confuses, 2),
                    TestAnswerVariantEntity(R.string.looser, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.same_pleasure, 0),
                    TestAnswerVariantEntity(R.string.not_same_pleasure, 1),
                    TestAnswerVariantEntity(R.string.no_real_pleasure, 2),
                    TestAnswerVariantEntity(R.string.no_pleasure, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_guilt, 0),
                    TestAnswerVariantEntity(R.string.often_feel_guilt, 1),
                    TestAnswerVariantEntity(R.string.almost_guilt, 2),
                    TestAnswerVariantEntity(R.string.total_guilt, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_punishment, 0),
                    TestAnswerVariantEntity(R.string.not_feel_punishment, 1),
                    TestAnswerVariantEntity(R.string.wait_for_punishment, 2),
                    TestAnswerVariantEntity(R.string.feel_punishment, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_disappoint, 0),
                    TestAnswerVariantEntity(R.string.disappoint, 1),
                    TestAnswerVariantEntity(R.string.disgust, 2),
                    TestAnswerVariantEntity(R.string.hate_myself, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.not_worth, 0),
                    TestAnswerVariantEntity(R.string.some_mistakes, 1),
                    TestAnswerVariantEntity(R.string.total_mistakes, 2),
                    TestAnswerVariantEntity(R.string.total_guilty, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_self_harm, 0),
                    TestAnswerVariantEntity(R.string.thought_about_die, 1),
                    TestAnswerVariantEntity(R.string.want_die, 2),
                    TestAnswerVariantEntity(R.string.firstly_die, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_cry, 0),
                    TestAnswerVariantEntity(R.string.often_cry, 1),
                    TestAnswerVariantEntity(R.string.all_time_cry, 2),
                    TestAnswerVariantEntity(R.string.can_t_cry, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_angry, 0),
                    TestAnswerVariantEntity(R.string.often_angry, 1),
                    TestAnswerVariantEntity(R.string.all_time_angry, 2),
                    TestAnswerVariantEntity(R.string.can_t_angry, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.have_interest, 0),
                    TestAnswerVariantEntity(R.string.less_interest, 1),
                    TestAnswerVariantEntity(R.string.almost_not_interest, 2),
                    TestAnswerVariantEntity(R.string.not_interest, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.decision_easy, 0),
                    TestAnswerVariantEntity(R.string.decision_latter, 1),
                    TestAnswerVariantEntity(R.string.decision_hard, 2),
                    TestAnswerVariantEntity(R.string.no_decisions, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.look_normal, 0),
                    TestAnswerVariantEntity(R.string.look_not_good, 1),
                    TestAnswerVariantEntity(R.string.look_bad, 2),
                    TestAnswerVariantEntity(R.string.look_horrible, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.good_working, 0),
                    TestAnswerVariantEntity(R.string.working_harder, 1),
                    TestAnswerVariantEntity(R.string.working_hard, 2),
                    TestAnswerVariantEntity(R.string.no_working, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.good_sleep, 0),
                    TestAnswerVariantEntity(R.string.not_good_sleep, 1),
                    TestAnswerVariantEntity(R.string.wake_up_earlier, 2),
                    TestAnswerVariantEntity(R.string.can_t_sleep, 3)
                )
            ),

            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_fatigue, 0),
                    TestAnswerVariantEntity(R.string.fatigue_faster, 1),
                    TestAnswerVariantEntity(R.string.fatigue, 2),
                    TestAnswerVariantEntity(R.string.total_fatigue, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.good_appetite, 0),
                    TestAnswerVariantEntity(R.string.not_good_appetite, 1),
                    TestAnswerVariantEntity(R.string.appetite_worse, 2),
                    TestAnswerVariantEntity(R.string.no_appetite, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_weight_loss, 0),
                    TestAnswerVariantEntity(R.string.loss_2_kg, 1),
                    TestAnswerVariantEntity(R.string.loss_4_kg, 2),
                    TestAnswerVariantEntity(R.string.loss_6_kg, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.no_health_anxiety, 0),
                    TestAnswerVariantEntity(R.string.some_health_troubles, 1),
                    TestAnswerVariantEntity(R.string.health_anxiety, 2),
                    TestAnswerVariantEntity(R.string.total_health_anxiety, 3)
                )
            ),
            TestQuestionEntity(
                listOf(
                    TestAnswerVariantEntity(R.string.good_sex, 0),
                    TestAnswerVariantEntity(R.string.less_sex, 1),
                    TestAnswerVariantEntity(R.string.very_less_sex, 2),
                    TestAnswerVariantEntity(R.string.no_sex, 3)
                )
            )
        )

    companion object {
        const val TEST_NAME = "beck depression test"
    }
}
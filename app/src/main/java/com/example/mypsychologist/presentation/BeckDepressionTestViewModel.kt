package com.example.mypsychologist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.TestAnswerVariantEntity
import com.example.mypsychologist.domain.entity.TestQuestionEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BeckDepressionTestViewModel : ViewModel() {
    private val questions = listOf(
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
                TestAnswerVariantEntity(R.string.no_dissatisfaction, 0),
                TestAnswerVariantEntity(R.string.no_joy, 1),
                TestAnswerVariantEntity(R.string.no_satisfaction, 2),
                TestAnswerVariantEntity(R.string.total_dissatisfaction, 3)
            )
        ),
        TestQuestionEntity(
            listOf(
                TestAnswerVariantEntity(R.string.no_fault, 0),
                TestAnswerVariantEntity(R.string.some_fault, 1),
                TestAnswerVariantEntity(R.string.fault, 2),
                TestAnswerVariantEntity(R.string.total_fault, 3)
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
                TestAnswerVariantEntity(R.string.no_disappoint, 0),
                TestAnswerVariantEntity(R.string.disappoint, 1),
                TestAnswerVariantEntity(R.string.disgust, 2),
                TestAnswerVariantEntity(R.string.hate_myself, 3)
            )
        ),
        TestQuestionEntity(
            listOf(
                TestAnswerVariantEntity(R.string.no_self_harm, 0),
                TestAnswerVariantEntity(R.string.better_die, 1),
                TestAnswerVariantEntity(R.string.plan_die, 2),
                TestAnswerVariantEntity(R.string.firstly_die, 3)
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
                TestAnswerVariantEntity(R.string.not_good_appetite, 3)
            )
        )
    )

    private val answers = MutableList(questions.size) { 0 }

    private var questionNumber = 0

    private val _screenState: MutableStateFlow<BeckDepressionScreenState> =
        MutableStateFlow(BeckDepressionScreenState.Question(questions[questionNumber], questionNumber))
    val screenState: StateFlow<BeckDepressionScreenState>
        get() =
            _screenState.asStateFlow()

    fun saveAnswerAndGoToNext(score: Int) {
        answers[questionNumber] = score

        nextQuestion()
    }

    private fun nextQuestion() {
        questionNumber += 1
        viewModelScope.launch {
            _screenState.emit(
                if (questionNumber < questions.size)
                    BeckDepressionScreenState.Question(questions[questionNumber], questionNumber)
                else {
                    val result = answers.sum()
                    BeckDepressionScreenState.Result(result, generateConclusionId(result))
                }
            )

        }
    }

    fun lastQuestion() {
        if (questionNumber > 0) {
            questionNumber -= 1

            viewModelScope.launch {
                _screenState.emit(BeckDepressionScreenState.Question(questions[questionNumber], questionNumber))
            }
        }
    }


    private fun generateConclusionId(score: Int): Int =
        when (score) {
            in 0..9 -> R.string.no_depression
            in 10..19 -> R.string.light_depression
            in 20..22 -> R.string.middle_depression
            else -> R.string.heave_depression
        }

}
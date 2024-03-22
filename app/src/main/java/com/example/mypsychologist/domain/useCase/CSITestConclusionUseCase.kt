package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.CSIResultEntity
import javax.inject.Inject

class CSITestConclusionUseCase @Inject constructor() {

    operator fun invoke(answers: List<Int>): CSIResultEntity = run {
        CSIResultEntity(
            problemResolutionStrategy = ProblemResolutionStrategy(answers).calculate(),
            strategyForSeekingSocialSupport = StrategyForSeekingSocialSupport(answers).calculate(),
            avoidanceStrategy = AvoidanceStrategy(answers).calculate(),
            )

    }

    class ProblemResolutionStrategy(answers: List<Int>) : Scale(answers) {
        override fun items() =
            arrayOf(1, 2, 7, 8, 10, 14, 15, 16, 19, 28, 32)

        override fun getConclusion() =
            when (score()) {
                in 0..16 -> R.string.very_low
                in 17..21 -> R.string.low
                in 22..30 -> R.string.medium
                else -> R.string.high
            }
    }

    class StrategyForSeekingSocialSupport(answers: List<Int>) : Scale(answers) {
        override fun items() =
            arrayOf(0, 4, 6, 11, 13, 18, 22, 23)

        override fun getConclusion() =
            when (score()) {
                in 0..13 -> R.string.very_low
                in 14..18 -> R.string.low
                in 19..28 -> R.string.medium
                else -> R.string.high
            }
    }

    class AvoidanceStrategy(answers: List<Int>) : Scale(answers) {
        override fun items() =
            arrayOf(3, 5, 9, 12, 17, 20, 21, 25, 26, 27)

        override fun getConclusion() =
            when (score()) {
                in 0..15 -> R.string.very_low
                in 16..23 -> R.string.low
                in 24..26 -> R.string.medium
                else -> R.string.high
            }
    }
}
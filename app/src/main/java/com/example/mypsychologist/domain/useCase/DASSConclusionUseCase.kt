package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.DASSResultEntity
import javax.inject.Inject

class DASSConclusionUseCase @Inject constructor() {

    operator fun invoke(answers: List<Int>): DASSResultEntity = run {
        DASSResultEntity(
            stressScoreAndConclusion = Stress(answers).calculate(),
            anxietyScoreAndConclusion = Anxiety(answers).calculate(),
            depressionScoreAndConclusion = Depression(answers).calculate()
        )
    }

    class Stress(answers: List<Int>) : Scale(answers) {
        override fun items() =
            arrayOf(0, 5, 7, 10, 11, 13, 17)

        override fun getConclusion(): Int =
            when (score()) {
                in 0..7 -> R.string.normal
                in 8..9 -> R.string.low
                in 10..12 -> R.string.moderately
                in 13..16 -> R.string.high
                else -> R.string.very_high
            }
    }

    class Anxiety(answers: List<Int>) : Scale(answers) {

        override fun items() =
            arrayOf(1, 3, 6, 8, 14, 18, 19)

        override fun getConclusion() =
            when (score()) {
                in 0..3 -> R.string.normal
                in 4..5 -> R.string.low
                in 6..7 -> R.string.moderately
                in 8..9 -> R.string.high
                else -> R.string.very_high
            }
    }

    class Depression(answers: List<Int>) : Scale(answers) {

        override fun items() =
            arrayOf(2, 4, 9, 12, 15, 16, 20)

        override fun getConclusion() =
            when (score()) {
                in 0..4 -> R.string.normal
                in 5..6 -> R.string.low
                in 7..10 -> R.string.moderately
                in 11..13 -> R.string.high
                else -> R.string.very_high
            }
    }

}
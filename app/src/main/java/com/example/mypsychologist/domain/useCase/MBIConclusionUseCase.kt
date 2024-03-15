package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.MBIResultEntity
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

class MBIConclusionUseCase @Inject constructor() {
    operator fun invoke(answers: List<Int>): MBIResultEntity = run {

//        val emotionalExhaustion = EmotionalExhaustion(answers).calculate().first
//        val depersonalization = Depersonalization(answers).calculate().first
//        val reductionOfPersonalAchievements = ReductionOfPersonalAchievements(answers).calculate().first
//        val index = sqrt(((emotionalExhaustion.toDouble()/54).pow(2) + (depersonalization.toDouble()/30).pow(2) + (1-reductionOfPersonalAchievements.toDouble()/48).pow(2))/3)

        MBIResultEntity(
            emotionalExhaustion = EmotionalExhaustion(answers).calculate(),
            depersonalization = Depersonalization(answers).calculate(),
            reductionOfPersonalAchievements = ReductionOfPersonalAchievements(answers).calculate(),

        )
    }

    class EmotionalExhaustion(answers: List<Int>) : Scale(answers) {
        override fun items() =
            arrayOf(0, 1, 2,5, 7, 12, 13, 15, 19)


        override fun getConclusion() =
            when (score()) {
                in 0..15 -> R.string.low
                in 16..24 -> R.string.medium
                else -> R.string.high
            }

    }

    class Depersonalization(answers: List<Int>) : Scale(answers) {
        override fun items() =
            arrayOf(4, 9, 10, 14, 21)

        override fun getConclusion(): Int =

            when (score()) {
                in 0..5 -> R.string.low
                in 6..10 -> R.string.medium
                else -> R.string.high
            }
    }

    class ReductionOfPersonalAchievements(answers: List<Int>) : Scale(answers) {
        override fun items() =
            arrayOf(3, 6, 8, 11, 16, 17, 18, 20)

        override fun getConclusion(): Int =

            when (score()) {
                in 0..30 -> R.string.high
                in 31..36 -> R.string.medium
                else -> R.string.low
            }
    }
}

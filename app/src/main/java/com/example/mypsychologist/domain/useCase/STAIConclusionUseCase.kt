package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.STAIResultEntity
import javax.inject.Inject

class STAIConclusionUseCase @Inject constructor() {

    operator fun invoke(answers: List<Int>): STAIResultEntity =
        STAIResultEntity(
            personalAnxiety = PersonalAnxiety(answers).calculate(),
            situationalAnxiety = SituationalAnxiety(answers).calculate()
        )


    class PersonalAnxiety(answers: List<Int>) : Scale(answers) {
        override fun items() =
            Array(20) { it + 10 }            // с 20 по 40

        override fun getConclusion(): Int =
            when (score()) {
                in 0..30 -> R.string.low
                in 31..44 -> R.string.medium
                else -> R.string.high
            }

    }

    class SituationalAnxiety(answers: List<Int>) : Scale(answers) {
        override fun items() =
            Array(20) { it }       // c 1 по 20

        override fun getConclusion(): Int =
            when (score()) {
                in 0..30 -> R.string.low
                in 31..44 -> R.string.medium
                else -> R.string.high
            }
    }
}
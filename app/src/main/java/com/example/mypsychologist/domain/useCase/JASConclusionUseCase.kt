package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.JASResultEntity
import javax.inject.Inject

class JASConclusionUseCase @Inject constructor() {

    operator fun invoke(answers: List<Int>): JASResultEntity = run {
        JASResultEntity(
            apatheticActions = ApatheticActions(answers).calculate(),
            apatheticThoughts = ApatheticThoughts(answers).calculate()
        )
    }

    class ApatheticActions(answers: List<Int>) : Scale(answers) {
        override fun items() =
            Array(5) { it + 5 }


        override fun getConclusion() =
            when (score()) {
                in 0..8 -> R.string.low
                in 9..16 -> R.string.medium
                else -> R.string.high
            }

    }

    class ApatheticThoughts(answers: List<Int>) : Scale(answers) {
        override fun items() =
            Array(5) { it }

        override fun getConclusion(): Int =

            when (score()) {
                in 0..8 -> R.string.low
                in 9..16 -> R.string.medium
                else -> R.string.high
            }
    }
}

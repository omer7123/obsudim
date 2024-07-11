package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.BeckDepressionResultEntity
import javax.inject.Inject

class BeckDepressionTestConclusionUseCase @Inject constructor() {
    operator fun invoke(answers: List<Int>): BeckDepressionResultEntity =
        BeckDepressionResultEntity(
            depressionScore = DepressionScore(answers).calculate(),
            cognitiveDepression = CognitiveDepression(answers).calculate(),
            somaticDepression = SomaticDepression(answers).calculate()
        )

    class DepressionScore(answers: List<Int>): Scale(answers) {
        override fun items() =
            Array(21) { it }

        override fun getConclusion(): Int =
            when (score())
            {
                in 0..9 -> R.string.no_depression
                in 10..15 -> R.string.light_depression
                in 16..19 -> R.string.moderate_depression
                in 20..29 -> R.string.middle_depression
                else -> R.string.heave_depression
            }
    }

    class CognitiveDepression(answers: List<Int>): Scale(answers) {
        override fun items() =
            Array(13) { it }

        override fun getConclusion(): Int =
            when (score())
            {
                in 0..12 -> R.string.no_cognitive_impairment
                in 13..26 -> R.string.middle_cognitive_impairment
                else -> R.string.strong_cognitive_impairment
            }
    }

    class SomaticDepression(answers: List<Int>): Scale(answers) {
        override fun items() =
            Array(8) { it + 13 }

        override fun getConclusion(): Int =
            when (score())
            {
                in 0..7 -> R.string.no_physical_symptoms
                in 8..16 -> R.string.middle_physical_symptoms
                else -> R.string.strong_physical_symptoms
            }
    }
}
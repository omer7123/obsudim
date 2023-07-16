package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import javax.inject.Inject

class BeckDepressionTestConclusionUseCase  @Inject constructor() {
    operator fun invoke(score: Int): Int =
        when (score) {
            in 0..9 -> R.string.no_depression
            in 10..19 -> R.string.light_depression
            in 20..22 -> R.string.middle_depression
            else -> R.string.heave_depression
        }
}
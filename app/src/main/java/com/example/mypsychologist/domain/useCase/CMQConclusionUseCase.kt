package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.R
import com.example.mypsychologist.domain.entity.ResultWithScalesEntity
import javax.inject.Inject

class CMQConclusionUseCase @Inject constructor() {

    private lateinit var answers: List<Int>

    operator fun invoke(answers: List<Int>): ResultWithScalesEntity = run {
        this.answers = answers
        ResultWithScalesEntity(answers.sum(), generateConclusion(), generateMapOfItems())
    }

    private fun generateConclusion(): Int =
        if (answers.sum() > MAX_NORMAL)
            R.string.cognitive_errors
        else
            R.string.normal

    private fun generateMapOfItems(): HashMap<Int, Int> =
        hashMapOf(
            R.string.personalization to sumElements(20, 16, 15, 19, 13),
            R.string.mind_reading to sumElements(14, 8, 17, 6, 9),
            R.string.stubbornness to sumElements(43, 43, 42, 45, 23),
            R.string.moralization to sumElements(11, 12, 39, 40, 21),
            R.string.disaster to sumElements(3, 1, 2, 10, 25),
            R.string.learned_helplessness to sumElements(26, 28, 4, 5, 36, 35, 34, 29, 28),
            R.string.maximalism to sumElements(22, 24, 18, 21, 25, 23),
            R.string.danger_exaggerating to sumElements(33, 34, 35, 23, 9, 31, 27),
            R.string.hypernormality to sumElements(37, 40, 32, 33, 41)
        )

    private fun sumElements(vararg indexes: Int): Int {
        var sum = 0
        indexes.forEach { i ->
            sum += answers[i - 1]
        }
        return sum
    }

    companion object {
        private const val MAX_NORMAL = 93
    }
}
package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.extensions.sum

abstract class Scale(private val answers: List<Int>) {
    fun calculate(): Pair<Int, Int> = run {
        Pair(
            score(),
            getConclusion()
        )
    }

    fun score() =
        answers.sum(items())

    abstract fun items(): Array<Int>
    abstract fun getConclusion(): Int
}

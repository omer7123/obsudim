package com.example.mypsychologist.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestQuestionEntity(
    val variants: List<TestAnswerVariantEntity>,
    val question: Int = 0
) : Parcelable

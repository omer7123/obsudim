package com.obsudim.mypsychologist.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestAnswerVariantEntity(
    val answerId: Int,
    val score: Int
) : Parcelable

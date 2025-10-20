package com.obsudim.mypsychologist.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagEntity (
    val id: Int,
    val text: String
) : Parcelable

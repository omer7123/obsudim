package com.example.mypsychologist.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClientRequestEntity(
    val name: String,
    val clientId: String,
    val text: String
) : Parcelable

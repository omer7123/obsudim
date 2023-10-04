package com.example.mypsychologist.domain.entity

import android.net.Uri

data class PsychologistData(
    val info: PsychologistInfo,
    val documents: List<Uri>
)

package com.example.mypsychologist.domain.entity

import com.example.mypsychologist.R

data class DiaryCard(
    val title: String,
    val description: String,
    val imageRes: Int = R.drawable.ic_auto_stories
)

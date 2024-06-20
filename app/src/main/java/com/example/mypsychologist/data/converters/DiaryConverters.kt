package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.FreeDiaryModel
import com.example.mypsychologist.domain.entity.FreeDiary

fun FreeDiaryModel.toFreeDiaryEntity(): FreeDiary {
    return FreeDiary(
        id = this.free_diary_id,
        text = this.text
    )
}
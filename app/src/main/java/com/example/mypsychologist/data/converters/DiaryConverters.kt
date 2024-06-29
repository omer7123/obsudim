package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.FreeDiaryModel
import com.example.mypsychologist.data.model.NewFreeDiaryModel
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity

fun FreeDiaryModel.toFreeDiaryEntity(): FreeDiaryEntity {
    return FreeDiaryEntity(
        id = this.free_diary_id,
        text = this.text
    )
}

fun NewFreeDiaryEntity.toNewFreeDiaryModel(): NewFreeDiaryModel {
    return NewFreeDiaryModel(this.text)
}
package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.FreeDiaryModel
import com.example.mypsychologist.data.model.MoodTrackerRespModel
import com.example.mypsychologist.data.model.NewFreeDiaryModel
import com.example.mypsychologist.data.model.SaveMoodModel
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodTrackerRespEntity
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity

fun FreeDiaryModel.toFreeDiaryEntity(): FreeDiaryEntity {
    return FreeDiaryEntity(
        id = this.freeDiaryId,
        text = this.text
    )
}

fun NewFreeDiaryEntity.toNewFreeDiaryModel(): NewFreeDiaryModel {
    return NewFreeDiaryModel(this.text)
}

fun SaveMoodEntity.toModel(): SaveMoodModel =
    SaveMoodModel(score, freeDiaryId, thinkDiaryId, diaryType)

fun MoodTrackerRespModel.toEntity(): MoodTrackerRespEntity = MoodTrackerRespEntity(moodTrackerId)
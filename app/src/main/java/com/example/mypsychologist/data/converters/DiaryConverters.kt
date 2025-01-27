package com.example.mypsychologist.data.converters

import com.example.mypsychologist.data.model.FreeDiaryModel
import com.example.mypsychologist.data.model.MoodTrackerPresentModel
import com.example.mypsychologist.data.model.MoodTrackerRespModel
import com.example.mypsychologist.data.model.NewFreeDiaryModel
import com.example.mypsychologist.data.model.NewFreeDiaryWithDateModel
import com.example.mypsychologist.data.model.SaveMoodModel
import com.example.mypsychologist.data.model.SaveMoodWithDateModel
import com.example.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodTrackerRespEntity
import com.example.mypsychologist.domain.entity.diaryEntity.MoodTrackerResultEntity
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryWithDateEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity
import com.example.mypsychologist.domain.entity.diaryEntity.SaveMoodWithDateEntity

fun FreeDiaryModel.toFreeDiaryEntity(): FreeDiaryEntity {
    return FreeDiaryEntity(
        id = this.freeDiaryId, text = this.text, createdAt = this.createdAt
    )
}

fun NewFreeDiaryEntity.toNewFreeDiaryModel(): NewFreeDiaryModel {
    return NewFreeDiaryModel(this.text)
}

fun NewFreeDiaryWithDateEntity.toNewFreeDiaryModel(): NewFreeDiaryWithDateModel {
    return NewFreeDiaryWithDateModel(this.text, this.createdAt)
}

fun SaveMoodEntity.toModel(): SaveMoodModel =
    SaveMoodModel(score, freeDiaryId, thinkDiaryId, diaryType)

fun MoodTrackerRespModel.toEntity(): MoodTrackerRespEntity = MoodTrackerRespEntity(moodTrackerId)
fun SaveMoodWithDateEntity.toModel(): SaveMoodWithDateModel {
    return SaveMoodWithDateModel(
        score = this.score, date = this.date
    )
}

fun MoodTrackerPresentModel.toEntity() =
    MoodTrackerResultEntity(
        id = id, score = score, date = date
    )


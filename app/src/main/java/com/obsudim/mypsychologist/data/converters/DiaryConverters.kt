package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.CalendarResponseModel
import com.obsudim.mypsychologist.data.model.EmojiModel
import com.obsudim.mypsychologist.data.model.FreeDiaryModel
import com.obsudim.mypsychologist.data.model.MoodTrackerPresentModel
import com.obsudim.mypsychologist.data.model.MoodTrackerRespModel
import com.obsudim.mypsychologist.data.model.NewFreeDiaryModel
import com.obsudim.mypsychologist.data.model.NewFreeDiaryWithDateModel
import com.obsudim.mypsychologist.data.model.SaveMoodModel
import com.obsudim.mypsychologist.data.model.SaveMoodWithDateModel
import com.obsudim.mypsychologist.domain.entity.diaryEntity.CalendarResponseEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.EmojiEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.FreeDiaryEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.MoodTrackerRespEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.MoodTrackerResultEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryWithDateEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.SaveMoodEntity
import com.obsudim.mypsychologist.domain.entity.diaryEntity.SaveMoodWithDateEntity

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
    SaveMoodModel(score = score, emojiIds = emojiIds)

fun MoodTrackerRespModel.toEntity(): MoodTrackerRespEntity = MoodTrackerRespEntity(status)

fun SaveMoodWithDateEntity.toModel(): SaveMoodWithDateModel {
    return SaveMoodWithDateModel(
        score = this.score, day = this.date, emojiIds = emojiIds
    )
}

fun MoodTrackerPresentModel.toEntity() =
    MoodTrackerResultEntity(
        id = id, score = score, date = date, emojiIds = emojiIds, emojiTexts = emojiTexts
    )

fun CalendarResponseModel.toEntity(): CalendarResponseEntity =
    CalendarResponseEntity(
        date = date,
        diary = diary
    )

fun EmojiModel.toEntity(): EmojiEntity{
    return EmojiEntity(
        id = id,
        text = text,
    )
}

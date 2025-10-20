package com.obsudim.mypsychologist.data.remote.freeDiary

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.CalendarResponseModel
import com.obsudim.mypsychologist.data.model.EmojiModel
import com.obsudim.mypsychologist.data.model.FreeDiaryModel
import com.obsudim.mypsychologist.data.model.MoodTrackerPresentModel
import com.obsudim.mypsychologist.data.model.MoodTrackerRespModel
import com.obsudim.mypsychologist.data.model.NewFreeDiaryWithDateModel
import com.obsudim.mypsychologist.data.model.SaveMoodModel
import com.obsudim.mypsychologist.data.model.SaveMoodWithDateModel
import kotlinx.coroutines.flow.Flow

interface FreeDiaryDataSource {
    suspend fun getFreeDiaries(): Resource<List<FreeDiaryModel>>
    suspend fun addFreeDiary(freeDiary: NewFreeDiaryWithDateModel): Resource<Unit>
    suspend fun getFreeDiariesByDate(date: String): Flow<Resource<List<FreeDiaryModel>>>
    suspend fun saveMoodTracker(saveMoodModel: SaveMoodModel): Flow<Resource<MoodTrackerRespModel>>
    suspend fun saveMoodTrackerWithDate(saveMoodModel: SaveMoodWithDateModel): Flow<Resource<MoodTrackerRespModel>>
    suspend fun getAllMoodTrackers(date: String): Flow<Resource<List<MoodTrackerPresentModel>>>
    suspend fun getDatesWithDiaries(month: Int): Flow<Resource<List<CalendarResponseModel>>>
    suspend fun getAllEmojies(): Flow<Resource<List<EmojiModel>>>
}
package com.obsudim.mypsychologist.data.remote.profile.gamification

import com.obsudim.mypsychologist.data.model.CurrentScoreModel
import com.obsudim.mypsychologist.data.model.WeeklyScoresModel
import retrofit2.Response
import retrofit2.http.GET

interface GamificationService {
    @GET("/gamification/current-score")
    suspend fun getCurrentScore(): Response<CurrentScoreModel>

    @GET("/gamification/weekly-score")
    suspend fun getWeeklyScores(): Response<WeeklyScoresModel>
}
package com.obsudim.mypsychologist.data.remote.gamification

import com.obsudim.mypsychologist.data.model.CurrentScoreModel
import com.obsudim.mypsychologist.data.model.WeeklyScoresModel
import retrofit2.Response
import retrofit2.http.GET

interface GamificationService {
    @GET("/gamification/current-score")
    suspend fun getCurrentScore(): Response<CurrentScoreModel>

    @GET("/gamification/weekly-scores")
    suspend fun getWeeklyScores(): Response<WeeklyScoresModel>
}
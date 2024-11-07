package com.example.mypsychologist.data.remote.exercises

import com.example.mypsychologist.data.model.ExercisesModel
import retrofit2.Response
import retrofit2.http.GET

interface ExerciseService {

    @GET("/exercise/get_all_exercises")
    suspend fun getAllExercises(): Response<List<ExercisesModel>>
}
package com.example.mypsychologist.data.repository

import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity
import com.example.mypsychologist.domain.repository.ExercisesRepository
import javax.inject.Inject

class ExercisesRepositoryImpl @Inject constructor() : ExercisesRepository {
    override fun getREBTProblemProgress(problem: String) =
        RebtProblemProgressEntity("Проблема", true, false, false, false)


}
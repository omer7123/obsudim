package com.example.mypsychologist.data.repository

import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.entity.RebtProblemProgressEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class RebtRepositoryImpl @Inject constructor() : RebtRepository {
    override fun getREBTProblemProgress(problemId: Int) =
        RebtProblemProgressEntity("Проблема1", true, false, false, false)

    override fun getCurrentREBTProblemProgress() =
        RebtProblemProgressEntity("Проблема", true, false, false, false)

    override fun getREBTProblems() = listOf(
        ProblemEntity(0,"Проблема 1", true, false),
        ProblemEntity(1,"Проблема 2", true, false),
        ProblemEntity(2,"Проблема 3", false, true),
        ProblemEntity(3,"Проблема 1", false, false),
        ProblemEntity(4,"Проблема 1", false, false),
    )
}
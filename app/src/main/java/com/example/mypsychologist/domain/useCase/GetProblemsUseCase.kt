package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ProblemEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class GetProblemsUseCase @Inject constructor(private val repository: RebtRepository) {

    suspend operator fun invoke(): Resource<List<ProblemEntity>> =
        repository.getREBTProblems()
}
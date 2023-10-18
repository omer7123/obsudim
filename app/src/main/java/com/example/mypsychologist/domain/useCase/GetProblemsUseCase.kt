package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class GetProblemsUseCase @Inject constructor(private val repository: RebtRepository) {

    suspend operator fun invoke() =
        repository.getREBTProblems()
}
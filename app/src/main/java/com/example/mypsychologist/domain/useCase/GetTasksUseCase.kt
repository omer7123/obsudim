package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val repository: PsychologistRepository) {
//    suspend operator fun invoke(psychologistId: String) =
//        repository.getTasks(psychologistId)
}
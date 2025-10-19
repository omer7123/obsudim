package com.obsudim.mypsychologist.domain.useCase.psychologistsUseCases

import com.obsudim.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val repository: PsychologistRepository) {
    suspend operator fun invoke() =
        repository.getTasks()
}
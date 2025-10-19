package com.obsudim.mypsychologist.domain.useCase.exerciseUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class MarkTaskUseCase @Inject constructor(private val repository: PsychologistRepository) {
    suspend operator fun invoke(taskId: String, isChecked: Boolean): Resource<String> =
        if (isChecked)
            repository.markTaskAsCompleted(taskId)
        else
            repository.markTaskAsNotCompleted(taskId)
}
package com.example.mypsychologist.domain.useCase.exerciseUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class MarkTaskUseCase @Inject constructor(private val repository: PsychologistRepository) {
    suspend operator fun invoke(taskId: String, isChecked: Boolean): Resource<String> =
        if (isChecked)
            repository.markTaskAsCompleted(taskId)
        else
            repository.markTaskAsNotCompleted(taskId,)
}
package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class MarkTaskUseCase @Inject constructor(private val repository: PsychologistRepository) {
//    operator fun invoke(taskId: String, psychologistId: String, isChecked: Boolean): Boolean =
//        if (isChecked)
//            repository.markTaskAsCompleted(taskId, psychologistId)
//        else
//            repository.markTaskAsNotCompleted(taskId, psychologistId)
}
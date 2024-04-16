package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(task: String, clientId: String) =
        repository.newTask(task, clientId)
}
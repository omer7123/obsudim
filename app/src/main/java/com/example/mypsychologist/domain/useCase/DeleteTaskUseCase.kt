package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(taskId: String, clientId: String) =
        repository.deleteTask(taskId, clientId)
}
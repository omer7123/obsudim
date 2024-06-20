package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class SendFeedbackUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(text: String) =
        repository.sendFeedback(text)
}
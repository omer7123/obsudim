package com.example.mypsychologist.domain.useCase.feedUseCases

import com.example.mypsychologist.domain.repository.FeedRepository
import javax.inject.Inject

class SendMessageInFeedUseCase @Inject constructor(private val repository: FeedRepository) {
    suspend operator fun invoke(message: String) =
        repository.send(message)
}
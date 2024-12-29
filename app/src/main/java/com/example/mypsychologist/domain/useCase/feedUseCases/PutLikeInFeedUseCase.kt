package com.example.mypsychologist.domain.useCase.feedUseCases

import com.example.mypsychologist.domain.repository.FeedRepository
import javax.inject.Inject

class PutLikeInFeedUseCase @Inject constructor(private val repository: FeedRepository) {
    suspend operator fun invoke(id: String) =
        repository.like(id)
}
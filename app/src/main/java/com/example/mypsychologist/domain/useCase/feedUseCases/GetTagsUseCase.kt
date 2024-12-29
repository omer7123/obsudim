package com.example.mypsychologist.domain.useCase.feedUseCases

import com.example.mypsychologist.domain.repository.TagsRepository
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(private val repository: TagsRepository) {
    suspend operator fun invoke() =
        repository.getTagsList()

}
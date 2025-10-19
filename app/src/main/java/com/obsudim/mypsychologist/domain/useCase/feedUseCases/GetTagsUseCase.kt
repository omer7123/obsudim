package com.obsudim.mypsychologist.domain.useCase.feedUseCases

import com.obsudim.mypsychologist.domain.repository.TagsRepository
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(private val repository: TagsRepository) {
    suspend operator fun invoke() =
        repository.getTagsList()

}
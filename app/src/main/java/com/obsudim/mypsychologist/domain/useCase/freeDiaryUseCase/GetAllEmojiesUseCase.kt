package com.obsudim.mypsychologist.domain.useCase.freeDiaryUseCase

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diaryEntity.EmojiEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllEmojiesUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(): Flow<Resource<List<EmojiEntity>>> = repository.getAllEmojies()
}
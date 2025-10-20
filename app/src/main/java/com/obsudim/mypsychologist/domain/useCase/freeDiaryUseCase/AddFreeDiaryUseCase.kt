package com.obsudim.mypsychologist.domain.useCase.freeDiaryUseCase

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryWithDateEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import javax.inject.Inject

class AddFreeDiaryUseCase @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(freeDiary: NewFreeDiaryWithDateEntity): Resource<Unit> =
        repository.addFreeDiary(freeDiary)
}
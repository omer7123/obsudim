package com.example.mypsychologist.domain.useCase.freeDiaryUseCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.diaryEntity.NewFreeDiaryWithDateEntity
import com.example.mypsychologist.domain.repository.retrofit.FreeDiaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFreeDiaryUseCaseWithDate @Inject constructor(private val repository: FreeDiaryRepository) {
    suspend operator fun invoke(data: NewFreeDiaryWithDateEntity): Flow<Resource<String>> =
        repository.addFreeDiaryWithDate(data)
}

package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.repository.retrofit.CbtRepository
import javax.inject.Inject

class GetThoughtDiaryUseCase @Inject constructor(private val repository: CbtRepository) {
    suspend operator fun invoke(id: String): Resource<ThoughtDiaryEntity> =
        repository.getThoughtDiary(id)
}
package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.ThoughtDiaryEntity
import com.example.mypsychologist.domain.repository.CbtRepository
import javax.inject.Inject

class SaveThoughtDiaryUseCase @Inject constructor(private val repository: CbtRepository) {
    suspend operator fun invoke(it: ThoughtDiaryEntity) =
        repository.saveThoughtDiary(it)
}


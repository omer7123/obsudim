package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.CbtRepository
import javax.inject.Inject

class GetThoughtDiaryUseCase @Inject constructor(private val repository: CbtRepository) {
    suspend operator fun invoke(id: String) =
        repository.getThoughtDiary(id)
}
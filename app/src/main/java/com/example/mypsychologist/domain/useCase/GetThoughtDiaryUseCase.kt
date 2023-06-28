package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.CbtRepository
import javax.inject.Inject

class GetThoughtDiaryUseCase @Inject constructor(private val repository: CbtRepository) {
    operator fun invoke(id: Int) =
        repository.getThoughtDiary(id)
}
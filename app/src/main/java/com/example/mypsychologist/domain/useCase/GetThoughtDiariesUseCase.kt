package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.CbtRepository
import javax.inject.Inject

class GetThoughtDiariesUseCase @Inject constructor(private val repository: CbtRepository) {
    operator fun invoke() =
        repository.getThoughtDiaries()
}
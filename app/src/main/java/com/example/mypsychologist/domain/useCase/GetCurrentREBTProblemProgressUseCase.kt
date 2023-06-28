package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class GetCurrentREBTProblemProgressUseCase @Inject constructor(private val repository: RebtRepository) {
    operator fun invoke() =
        repository.getCurrentREBTProblemProgress()
}
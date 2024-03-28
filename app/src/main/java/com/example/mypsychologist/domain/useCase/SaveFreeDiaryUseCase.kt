package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.FreeDiaryEntity
import com.example.mypsychologist.domain.repository.CbtRepository
import javax.inject.Inject

class SaveFreeDiaryUseCase @Inject constructor(private val repository: CbtRepository) {
    operator fun invoke(it: FreeDiaryEntity) =
        repository.saveFreeDiary(it)
}



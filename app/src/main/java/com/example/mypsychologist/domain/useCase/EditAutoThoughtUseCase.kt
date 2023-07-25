package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.CbtRepository
import javax.inject.Inject

class EditAutoThoughtUseCase @Inject constructor(private val repository: CbtRepository) {
    operator fun invoke(diaryId: String, newText: String) =
        repository.editAutoThought(diaryId, newText)
}
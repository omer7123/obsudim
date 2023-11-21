package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.entity.AutoDialogMessageEntity
import com.example.mypsychologist.domain.repository.RebtRepository
import javax.inject.Inject

class SaveAutoDialogMessageUseCase @Inject constructor(private val repository: RebtRepository) {
    suspend operator fun invoke(message: AutoDialogMessageEntity): String =
        repository.saveDialogMessage(message)
}
package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class ChangeRequestUseCase @Inject constructor(private val repository: ProfileRepository) {
    operator fun invoke(it: List<String>) =
        repository.changeRequest(it)
}
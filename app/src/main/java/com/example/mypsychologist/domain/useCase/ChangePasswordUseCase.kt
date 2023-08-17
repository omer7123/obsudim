package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(it: String) =
        repository.changePassword(it)
}
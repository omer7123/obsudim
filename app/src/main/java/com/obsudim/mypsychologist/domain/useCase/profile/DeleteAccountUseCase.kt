package com.obsudim.mypsychologist.domain.useCase.profile

import com.obsudim.mypsychologist.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteAccountUseCase@Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke() =
        repository.deleteAccount()
}
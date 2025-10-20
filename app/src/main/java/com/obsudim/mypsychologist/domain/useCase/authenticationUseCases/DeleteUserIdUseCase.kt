package com.obsudim.mypsychologist.domain.useCase.authenticationUseCases

import com.obsudim.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class DeleteUserIdUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(): Unit = repository.deleteUserId()
}
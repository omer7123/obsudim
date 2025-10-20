package com.obsudim.mypsychologist.domain.useCase.authenticationUseCases

import com.obsudim.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class DeleteTokenUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    suspend operator fun invoke(): Unit = repository.deleteToken()
}
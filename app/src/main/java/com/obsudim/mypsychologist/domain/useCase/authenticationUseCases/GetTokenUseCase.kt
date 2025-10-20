package com.obsudim.mypsychologist.domain.useCase.authenticationUseCases

import com.obsudim.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    suspend operator fun invoke(): String =
        repository.getToken()
}
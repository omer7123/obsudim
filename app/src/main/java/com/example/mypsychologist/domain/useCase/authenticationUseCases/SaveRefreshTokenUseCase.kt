package com.example.mypsychologist.domain.useCase.authenticationUseCases

import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class SaveRefreshTokenUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(token: String): Unit = repository.saveRefToken(token)
}
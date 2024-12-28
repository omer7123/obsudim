package com.example.mypsychologist.domain.useCase.authenticationUseCases

import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class SaveUserIdUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    suspend operator fun invoke(userId: String): Unit = repository.saveUserId(userId)
}
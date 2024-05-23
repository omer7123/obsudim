package com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases

import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(token: String)=
        repository.saveToken(token)
}
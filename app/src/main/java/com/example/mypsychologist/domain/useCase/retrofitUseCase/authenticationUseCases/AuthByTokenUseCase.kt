package com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases

import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class AuthByTokenUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    suspend operator fun invoke(token: Token) =
        repository.authByToken(token)

}
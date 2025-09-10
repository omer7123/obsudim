package com.example.mypsychologist.domain.useCase.authenticationUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.entity.authenticationEntity.Tokens
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class AuthByTokenUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    suspend operator fun invoke(token: Token): Resource<Tokens> =
        repository.authByToken(token)

}
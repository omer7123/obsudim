package com.obsudim.mypsychologist.domain.useCase.authenticationUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.model.Token
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.Tokens
import com.obsudim.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class AuthByTokenUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    suspend operator fun invoke(token: Token): Resource<Tokens> =
        repository.authByToken(token)

}
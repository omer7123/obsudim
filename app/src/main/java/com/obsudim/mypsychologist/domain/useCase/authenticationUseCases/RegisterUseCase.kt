package com.obsudim.mypsychologist.domain.useCase.authenticationUseCases

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.RegisterEntity
import com.obsudim.mypsychologist.domain.entity.authenticationEntity.Tokens
import com.obsudim.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    suspend operator fun invoke(register: RegisterEntity): Resource<Tokens> =
        repository.oldRegister(register)
}
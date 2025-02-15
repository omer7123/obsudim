package com.example.mypsychologist.domain.useCase.authenticationUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.authenticationEntity.RegisterEntity
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthenticationRepository) {

    suspend operator fun invoke(register: RegisterEntity): Resource<User> =
        repository.oldRegister(register)
}
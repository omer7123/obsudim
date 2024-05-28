package com.example.mypsychologist.domain.useCase.retrofitUseCase.authenticationUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.data.model.Token
import com.example.mypsychologist.domain.entity.authenticationEntity.Register
import com.example.mypsychologist.domain.entity.authenticationEntity.User
import com.example.mypsychologist.domain.repository.retrofit.AuthenticationRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend fun register(register: Register): Resource<User> {
        return repository.register(register)
    }

    suspend fun registerOld(register: OldRegister): Resource<Token>{
        return repository.oldRegister(register)
    }

}
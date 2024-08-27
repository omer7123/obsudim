package com.example.mypsychologist.domain.useCase.retrofitUseCase.psychologistsUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.psychologistsEntity.MyPsychologistEntity
import com.example.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class GetYourPsychologistsUseCase @Inject constructor(private val repository: PsychologistRepository) {

    suspend operator fun invoke(): Resource<List<MyPsychologistEntity>> =
        repository.getYourPsychologists()
}
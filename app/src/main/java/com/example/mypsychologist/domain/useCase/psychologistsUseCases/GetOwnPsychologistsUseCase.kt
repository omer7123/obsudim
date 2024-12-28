package com.example.mypsychologist.domain.useCase.psychologistsUseCases

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class GetOwnPsychologistsUseCase @Inject constructor(private val repository: PsychologistRepository) {
    suspend operator fun invoke(): Resource<List<ManagerEntity>> =
        repository.getOwnPsychologists()
}
package com.example.mypsychologist.domain.useCase

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.PsychologistWithTaskCount
import com.example.mypsychologist.domain.entity.psychologistsEntity.ManagerEntity
import com.example.mypsychologist.domain.repository.PsychologistRepository
import javax.inject.Inject

class GetOwnPsychologistsUseCase @Inject constructor(private val repository: PsychologistRepository) {
    suspend operator fun invoke(): Resource<List<ManagerEntity>> =
        repository.getOwnPsychologists()
}
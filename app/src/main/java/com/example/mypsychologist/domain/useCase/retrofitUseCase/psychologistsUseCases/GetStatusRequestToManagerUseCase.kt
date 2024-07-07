package com.example.mypsychologist.domain.useCase.retrofitUseCase.psychologistsUseCases

import com.example.mypsychologist.domain.repository.retrofit.PsychologistRepository
import javax.inject.Inject

class GetStatusRequestToManagerUseCase @Inject constructor(private val repository: PsychologistRepository) {

    suspend operator fun invoke(): Boolean = repository.getStatusToRequestManager()
}
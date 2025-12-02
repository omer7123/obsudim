package com.obsudim.mypsychologist.data.repository

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.converters.toEntity
import com.obsudim.mypsychologist.data.converters.toModel
import com.obsudim.mypsychologist.data.remote.modelDepression.ModelDeprDataSource
import com.obsudim.mypsychologist.domain.entity.ModelDepressionPostReqEntity
import com.obsudim.mypsychologist.domain.entity.ModelDepressionRespEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.ModelDeprRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ModelDeprRepositoryImpl @Inject constructor(private val dataSource: ModelDeprDataSource): ModelDeprRepository {

    override suspend fun postNote(note: ModelDepressionPostReqEntity): Flow<Resource<ModelDepressionRespEntity>> =
        dataSource.postNote(note.toModel()).checkResource {
            it.toEntity()
        }

}
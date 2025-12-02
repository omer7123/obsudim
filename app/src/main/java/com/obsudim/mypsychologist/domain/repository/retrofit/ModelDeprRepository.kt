package com.obsudim.mypsychologist.domain.repository.retrofit

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.ModelDepressionPostReqEntity
import com.obsudim.mypsychologist.domain.entity.ModelDepressionRespEntity
import kotlinx.coroutines.flow.Flow

interface ModelDeprRepository {
    suspend fun postNote(note: ModelDepressionPostReqEntity): Flow<Resource<ModelDepressionRespEntity>>
}
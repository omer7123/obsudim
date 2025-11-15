package com.obsudim.mypsychologist.data.converters

import com.obsudim.mypsychologist.data.model.ModelDepressionPostReq
import com.obsudim.mypsychologist.data.model.ModelDepressionResp
import com.obsudim.mypsychologist.domain.entity.ModelDepressionPostReqEntity
import com.obsudim.mypsychologist.domain.entity.ModelDepressionRespEntity

fun ModelDepressionPostReqEntity.toModel(): ModelDepressionPostReq = ModelDepressionPostReq(text)

fun ModelDepressionResp.toEntity() = ModelDepressionRespEntity(prediction, probabilityNegative, probabilityPositive, text)
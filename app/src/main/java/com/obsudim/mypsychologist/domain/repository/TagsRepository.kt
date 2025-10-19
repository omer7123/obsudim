package com.obsudim.mypsychologist.domain.repository

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.domain.entity.TagEntity

interface TagsRepository {
    suspend fun getTagsList(): Resource<List<TagEntity>>
}
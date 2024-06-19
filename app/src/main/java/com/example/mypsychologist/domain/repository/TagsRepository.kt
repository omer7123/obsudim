package com.example.mypsychologist.domain.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.domain.entity.TagEntity

interface TagsRepository {
    suspend fun getTagsList(): Resource<List<TagEntity>>
}
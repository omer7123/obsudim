package com.example.mypsychologist.data.repository

import android.util.Log
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.remote.tags.TagsDataSource
import com.example.mypsychologist.domain.entity.TagEntity
import com.example.mypsychologist.domain.repository.TagsRepository
import javax.inject.Inject

class TagsRepositoryImpl @Inject constructor(private val dataSource: TagsDataSource) :
    TagsRepository {

    override suspend fun getTagsList(): Resource<List<TagEntity>> =
        when (val result = dataSource.getTags()) {
            is Resource.Error -> {
                Log.d("Tags Error", result.msg.toString())
                Resource.Error(result.msg.toString(), null)
            }
            is Resource.Loading -> Resource.Loading
            is Resource.Success ->
                Resource.Success(result.data.map { it.toEntity() })
        }
}
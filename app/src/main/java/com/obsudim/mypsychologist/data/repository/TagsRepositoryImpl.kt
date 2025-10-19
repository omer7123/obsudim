package com.obsudim.mypsychologist.data.repository

import android.util.Log
import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.converters.toEntity
import com.obsudim.mypsychologist.data.remote.tags.TagsDataSource
import com.obsudim.mypsychologist.domain.entity.TagEntity
import com.obsudim.mypsychologist.domain.repository.TagsRepository
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
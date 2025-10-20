package com.obsudim.mypsychologist.data.repository

import com.obsudim.mypsychologist.core.Resource
import com.obsudim.mypsychologist.data.converters.toEntity
import com.obsudim.mypsychologist.data.converters.toModel
import com.obsudim.mypsychologist.data.model.EduSaveResp
import com.obsudim.mypsychologist.data.remote.education.EducationDataSource
import com.obsudim.mypsychologist.di.ApiUrlProvider
import com.obsudim.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.obsudim.mypsychologist.domain.entity.educationEntity.TopicEntity
import com.obsudim.mypsychologist.domain.repository.retrofit.EducationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EducationRepositoryImpl @Inject constructor(
    private val dataSource: EducationDataSource,
    private val urlProvider: ApiUrlProvider
) :
    EducationRepository {

    override suspend fun getAllTheme(): Resource<List<TopicEntity>> {
        return when (val res = dataSource.getAllTheme()) {
            is Resource.Error -> Resource.Error(res.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(res.data.map { it.toEntity(urlProvider.url) })
        }
    }

    override suspend fun getEducationMaterial(id: String): Flow<Resource<TopicEntity>> =
        dataSource.getEducationMaterial(id).checkResource {
            it.toEntity(urlProvider.url)
        }


    override suspend fun saveProgress(educationMaterialForSaveProgressEntity: EducationMaterialForSaveProgressEntity): Resource<EduSaveResp> =
        when(val result = dataSource.saveProgress(educationMaterialForSaveProgressEntity.toModel())){
            is Resource.Error -> Resource.Error(result.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data)
        }

}
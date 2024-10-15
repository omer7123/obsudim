package com.example.mypsychologist.data.repository

import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.converters.toModel
import com.example.mypsychologist.data.model.EduSaveResp
import com.example.mypsychologist.data.remote.education.EducationDataSource
import com.example.mypsychologist.domain.entity.educationEntity.EducationMaterialForSaveProgressEntity
import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.domain.entity.educationEntity.ThemeEntity
import com.example.mypsychologist.domain.repository.EducationRepository
import javax.inject.Inject

class EducationRepositoryImpl @Inject constructor(
    private val dataSource: EducationDataSource
) :
    EducationRepository {
    override suspend fun getAllTheme(): Resource<List<ThemeEntity>> {
        return when (val res = dataSource.getAllTheme()) {
            is Resource.Error -> Resource.Error(res.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(res.data.map { it.toEntity() })
        }
    }

    override suspend fun getEducationMaterial(id: String): Resource<List<ItemMaterialEntity>> =
        when (val result = dataSource.getEducationMaterial(id)) {
            is Resource.Error -> Resource.Error(result.msg.toString(), null)
            is Resource.Success -> Resource.Success(result.data.map { item ->
                item.toEntity()
            })

            is Resource.Loading -> Resource.Loading
        }

    override suspend fun saveProgress(educationMaterialForSaveProgressEntity: EducationMaterialForSaveProgressEntity): Resource<EduSaveResp> =
        when(val result = dataSource.saveProgress(educationMaterialForSaveProgressEntity.toModel())){
            is Resource.Error -> Resource.Error(result.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(result.data)
        }

}
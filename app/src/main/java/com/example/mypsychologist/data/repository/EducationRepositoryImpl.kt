package com.example.mypsychologist.data.repository

import com.example.mypsychologist.R
import com.example.mypsychologist.core.Resource
import com.example.mypsychologist.data.converters.toEntity
import com.example.mypsychologist.data.remote.education.EducationDataSource
import com.example.mypsychologist.domain.entity.EducationTopicEntity
import com.example.mypsychologist.domain.entity.educationEntity.ThemeEntity
import com.example.mypsychologist.domain.repository.EducationRepository
import com.example.mypsychologist.domain.useCase.GetEducationMaterialUseCase
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class EducationRepositoryImpl @Inject constructor(private val reference: DatabaseReference, private val dataSource: EducationDataSource) :
    EducationRepository {
    override suspend fun getAllTheme(): Resource<List<ThemeEntity>> {
        return when(val res = dataSource.getAllTheme()){
            is Resource.Error -> Resource.Error(res.msg.toString(), null)
            Resource.Loading -> Resource.Loading
            is Resource.Success -> Resource.Success(res.data.map { it.toEntity() })
        }
    }

    override suspend fun getProgress(topic: String): Int =
        suspendCoroutine { continuation ->

            reference.child(EDUCATION).child(topic).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume((snapshot.value ?: 0).toString().toInt())
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

    override suspend fun getTopics(): List<EducationTopicEntity> =
        topics
      /*  withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            topics.pmap { it.copy(currentCard = getProgress(it.tag.toString())) }
        } */

    override suspend fun saveProgress(topic: String, progress: Int) {
        reference.child(EDUCATION).child(topic).setValue(progress)
    }

    override fun getBase(): Int =
        R.array.base

    override fun getCBTBase(): Int =
        R.array.cbt_base

    override fun getBurnout(): Int =
        R.array.burnout

    override fun getBreathingTechniques(): Int =
        R.array.breathing_techniques


    override fun getRelaxationTechniques(): Int =
        R.array.relaxation_techniques

    override fun getCopingStrategies(): Int =
        R.array.coping_strategies


    private val topics = listOf(
        EducationTopicEntity(R.string.psychology_base, BASE_COUNT, GetEducationMaterialUseCase.Topic.BASE),
        EducationTopicEntity(R.string.cbt_base, CBT_COUNT, GetEducationMaterialUseCase.Topic.CBT_BASE),
        EducationTopicEntity(R.string.burnout, BURNOUT_COUNT, GetEducationMaterialUseCase.Topic.BURNOUT),
        EducationTopicEntity(R.string.breathing_techniques, BREATHING_TECHNIQUES_COUNT, GetEducationMaterialUseCase.Topic.BREATHING_TECHNIQUES),
        EducationTopicEntity(R.string.relaxation_techniques, RELAXATION_TECHNIQUES_COUNT, GetEducationMaterialUseCase.Topic.RELAXATION_TECHNIQUES),
        EducationTopicEntity(R.string.strategies_coping_with_stress, COPING_STRATEGIES_COUNT, GetEducationMaterialUseCase.Topic.COPING_STRATEGIES),
    )

    companion object {
        const val EDUCATION = "education"
        private const val BASE_COUNT = 3
        private const val CBT_COUNT = 16
        private const val BURNOUT_COUNT = 9
        private const val BREATHING_TECHNIQUES_COUNT = 9
        private const val RELAXATION_TECHNIQUES_COUNT = 8
        private const val COPING_STRATEGIES_COUNT = 9
    }
}
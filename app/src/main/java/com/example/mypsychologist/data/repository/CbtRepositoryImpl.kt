package com.example.mypsychologist.data.repository

import com.example.mypsychologist.domain.entity.DiaryEntity
import com.example.mypsychologist.domain.entity.DiaryRecordEntity
import com.example.mypsychologist.domain.repository.CbtRepository
import javax.inject.Inject

class CbtRepositoryImpl @Inject constructor() : CbtRepository {

    override fun getThoughtDiaries(): List<DiaryRecordEntity> =
        listOf(
            DiaryRecordEntity(0,
                "Триггерная ситуация, которая произошла с клиентом и вызвала нежелательную реакцию"),
            DiaryRecordEntity(2,
                "Триггерная ситуация, которая произошла с клиентом и вызвала нежелательную реакцию"),
            DiaryRecordEntity(3,
                "Триггерная ситуация, которая произошла с клиентом и вызвала нежелательную реакцию")
        )

    override fun getThoughtDiary(id: Int): DiaryEntity =
        DiaryEntity(
            "Ондатр сломал плотину",
            "гнев 80%",
            "ондатр редиска",
            "плотина сломана",
            "ондатр пытается починить плотину",
            "ондатр средней редисости",
            "гнев 30%"
        )

    override fun saveThoughtDiary(it: DiaryEntity): Boolean =
        true
}
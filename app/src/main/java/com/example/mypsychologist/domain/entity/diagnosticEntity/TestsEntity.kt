package com.example.mypsychologist.domain.entity.diagnosticEntity

data class TestEntity(
    val title: String,
    val description: String,
    val descShort: String,
    val testId: String
)

data class SaveTestResultEntity(
    val date: String,
    val results: List<ScaleResultEntity>,
    val testId: String
)

data class ScaleResultEntity(
    val scaleId: String,
    val score: Int
)

data class TestResultsGetEntity(
    val testId: String,
    val testResultId: String,
    val datetime: String,
    val scaleResults: List<ScaleResultEntity>
)

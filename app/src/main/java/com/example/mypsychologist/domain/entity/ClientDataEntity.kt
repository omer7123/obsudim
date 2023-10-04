package com.example.mypsychologist.domain.entity

data class ClientDataEntity (
    val name: String,
    val birthday: Long,
    val gender: String,
    val diagnosis: String,
    val request: List<String>,
    val mail: String,
    val phone: String,
    val password: String = "***"
)

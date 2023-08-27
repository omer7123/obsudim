package com.example.mypsychologist.domain.entity

data class ClientInfoEntity(
        val name: String,
        val birthday: Long,
        val gender: String,
        val diagnosis: String,
        val request: List<String>
)
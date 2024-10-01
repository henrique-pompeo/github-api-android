package com.example.feature.pulls.domain.model

data class PullModel(
    val title: String,
    val body: String?,
    val createdAt: String,
    val user: PullUserModel
)

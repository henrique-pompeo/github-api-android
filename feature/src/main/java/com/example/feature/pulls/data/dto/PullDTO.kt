package com.example.feature.pulls.data.dto

import com.google.gson.annotations.SerializedName

data class PullDTO(
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("user") val user: PullUserDTO
)

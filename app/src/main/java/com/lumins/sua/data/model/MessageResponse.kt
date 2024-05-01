package com.lumins.sua.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    @SerialName("role")
    val role: String? = null,
    @SerialName("content")
    val message: String? = null,
    val error: String? = null
)

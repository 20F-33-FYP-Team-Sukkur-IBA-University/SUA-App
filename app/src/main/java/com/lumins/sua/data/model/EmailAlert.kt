package com.lumins.sua.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmailAlertKtor(
    @SerialName("_id")
    val id: String,
    val from: String,
    val subject: String,
    val body: String,
    val links: List<String>,
)
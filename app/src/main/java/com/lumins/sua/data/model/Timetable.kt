package com.lumins.sua.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    @SerialName("course")
    val courseName: String,
    val time: String,
    val day: String,
    val room: String?,
    val teacher: String?,
)


@Serializable
data class TimetableKtor(
    @SerialName("_id")
    val id: String,
    @SerialName("class")
    val className: String,
    val courses: List<Course>
)

@Serializable
data class TimetableResponse(
    val timeTable: List<TimetableKtor>
)
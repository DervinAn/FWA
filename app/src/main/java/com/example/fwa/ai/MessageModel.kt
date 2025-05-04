package com.example.fwa.ai

import kotlinx.serialization.Serializable


@Serializable
data class MessageModel(
    val message : String,
    val role : String,
)

package com.example.fwa.entities

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isUser: Boolean, // True if sent by user, false if bot
    val timestamp: Long = System.currentTimeMillis()
)

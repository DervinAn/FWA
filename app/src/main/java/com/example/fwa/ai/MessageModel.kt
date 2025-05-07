package com.example.fwa.ai

import kotlinx.serialization.Serializable


// Data classes for API request and response
data class ChatRequest(val prompt: String)
data class ChatResponse(val response: String)
data class Message(
    val role: String,   // "user" or "assistant"
    val content: String // the message text
)

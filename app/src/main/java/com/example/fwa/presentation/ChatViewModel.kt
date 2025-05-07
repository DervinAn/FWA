package com.example.fwa.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fwa.ai.ChatRequest
import com.example.fwa.ai.DeepSeekAPI
import com.example.fwa.ai.Message
import com.example.fwa.ai.retrofit
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val deepSeekAPI = retrofit.create(DeepSeekAPI::class.java)

    var messages = mutableListOf<Message>()

    fun sendMessage(content: String) {
        // Add user message to list
        messages.add(Message(role = "user", content = content))

        viewModelScope.launch {
            try {
                val response = deepSeekAPI.getChatResponse(ChatRequest(content))
                val reply = response.response
                messages.add(Message(role = "assistant", content = reply))
            } catch (e: Exception) {
                messages.add(Message(role = "assistant", content = "Sorry, something went wrong."))
            }
        }
    }
}
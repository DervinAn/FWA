package com.example.fwa

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fwa.ai.Constants
import com.example.fwa.ai.MessageModel
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel : ViewModel() {

    val messageList = mutableStateListOf<MessageModel>()

    private val chatModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = Constants.apiKey
    ).startChat()

    fun sendMessage(userText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    messageList.add(MessageModel(userText, "user"))
                    messageList.add(MessageModel("Typing...", "model"))
                }

                Log.d("Gemini", "Sending: $userText")
                val response = chatModel.sendMessage(userText)
                val reply = response.text ?: "No response"

                Log.d("Gemini", "Received: $reply")

                withContext(Dispatchers.Main) {
                    if (messageList.isNotEmpty()) {
                        messageList.removeLast()
                    }

                    // Safely clean the text of control/unicode characters
                    val cleanReply = reply.replace(Regex("[^\\x20-\\x7E\\n]"), "")
                    messageList.add(MessageModel(cleanReply, "model"))
                }

            } catch (e: Exception) {
                Log.e("Gemini", "Error during sendMessage()", e)
                withContext(Dispatchers.Main) {
                    if (messageList.isNotEmpty()) {
                        messageList.removeLast()
                    }
                    messageList.add(MessageModel("Error: ${e.localizedMessage}", "model"))
                }
            }
        }
    }
}

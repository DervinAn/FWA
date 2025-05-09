package com.example.fwa.ai

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

fun main() {
    askAi("Translate English to French: Hello, how are you?") { response ->
        println("AI says: $response")
    }
}

fun askAi(prompt: String, onResponse: (String) -> Unit) {
    val client = OkHttpClient()

    val requestBody = RequestBody.create(
        "application/json".toMediaTypeOrNull(),
        """{ "inputs": "$prompt" }"""
    )

    val request = Request.Builder()
        .url("https://api-inference.huggingface.co/models/tiiuae/falcon-7b-instruct")
        .addHeader("Authorization", "Bearer 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")
        .addHeader("Content-Type", "application/json")
        .post(requestBody)
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onResponse("❌ Error: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            println("Raw response body: $body")

            try {
                val jsonArray = JSONArray(body)
                val answer = jsonArray.getJSONObject(0).optString("generated_text")
                onResponse(answer)
            } catch (e: Exception) {
                onResponse("❌ Failed to parse AI response: ${e.message}")
            }
        }
    })
}



//fun main() {
//    askAi("Tell me a joke") { response ->
//        println("AI says: $response")
//    }
//}
//
//fun askAi(prompt: String, onResponse: (String) -> Unit) {
//    val client = OkHttpClient()
//
//    val requestBody = RequestBody.create(
//        "application/json".toMediaTypeOrNull(),
//        """
//    {
//      "model": "o1-mini",
//      "messages": [
//        {"role": "user", "content": "$prompt"}
//      ]
//    }
//    """.trimIndent()
//    )
//
//    val request = Request.Builder()
//        .url("https://api.openai.com/v1/chat/completions")
//        .addHeader("Authorization", "Bearer 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")
//        .addHeader("Content-Type", "application/json")
//        .post(requestBody)
//        .build()
//
//
//    client.newCall(request).enqueue(object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//            onResponse("❌ Error: ${e.message}")
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            response.use {
//                val body = it.body?.string()
//
//                // Debugging: Print the raw response body
//                println("Raw response body: $body")
//
//                if (body.isNullOrEmpty()) {
//                    onResponse("❌ Empty response from API.")
//                    return
//                }
//
//                try {
//                    // Parse the response
//                    val jsonResponse = JSONObject(body)
//                    val answer = jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text")
//
//                    if (answer.isNotEmpty()) {
//                        onResponse(answer.trim())
//                    } else {
//                        onResponse("❌ No generated text found in the response.")
//                    }
//                } catch (e: Exception) {
//                    onResponse("❌ Failed to parse AI response: ${e.message}")
//                }
//            }
//        }
//    })
//}

//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
//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
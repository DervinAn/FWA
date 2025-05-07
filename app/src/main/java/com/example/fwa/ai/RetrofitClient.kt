package com.example.fwa.ai


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


val retrofit = Retrofit.Builder()
    .baseUrl("https://api.deepseek.com/") // Replace with DeepSeek's actual base URL
    .addConverterFactory(GsonConverterFactory.create())
    .build()


interface DeepSeekAPI {
    @Headers("Authorization: Bearer sk-ac57311753b3482a99dcf34a6f4927f5")
    @POST("v1/chat")
    suspend fun getChatResponse(@Body request: ChatRequest): ChatResponse
}


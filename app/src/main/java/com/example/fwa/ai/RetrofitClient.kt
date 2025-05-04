package com.example.fwa.ai


import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object HuggingFaceClient {
    private const val BASE_URL = "https://api-inference.huggingface.co/" // Change the model if you prefer
    private const val API_KEY = "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" // Replace with your Hugging Face API key

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: HuggingFaceApi = retrofit.create(HuggingFaceApi::class.java)
}

interface HuggingFaceApi {
    @POST("models/HuggingFaceH4/zephyr-7b-beta")
    suspend fun queryModel(@Body input: Map<String, String>): Response<List<Output>>
}

data class Output(val generated_text: String)

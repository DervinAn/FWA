package com.example.fwp

data class FoodPost(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val availableUntil: Long = 0L, // timestamp
    val location: String = "",
    val status: String = "available" // or "taken"
)

package com.example.fwp

import com.google.firebase.firestore.Exclude

data class Recipe(
    @get:Exclude val id: String = "",
    val title: String = "",
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList(),
    val imageUrl: String = "",
    val postedBy: String = ""
)

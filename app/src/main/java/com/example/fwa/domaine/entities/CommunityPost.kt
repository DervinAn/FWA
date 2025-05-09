package com.example.fwp


data class CommunityPost(
    val id: String = "",
    val userId: String = "",
    val content: String = "",
    val imageUrl: String = "",
    val timestamp: Long = 0L,
    val likedBy: List<String> = emptyList(),         // User IDs who liked the post
    val comments: List<Comment> = emptyList()      // (Optional) User IDs who commented
)

data class Comment(
    val userId: String = "",
    val message: String = "",
    val timestamp: Long = 0L
)


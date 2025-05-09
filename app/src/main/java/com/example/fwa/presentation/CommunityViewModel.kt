    package com.example.fwa.presentation

    import android.util.Log
    import androidx.lifecycle.ViewModel
    import com.example.fwp.Comment
    import com.example.fwp.CommunityPost
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.firestore.FirebaseFirestore
    import com.google.firebase.firestore.Query
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow

    class CommunityViewModel : ViewModel() {
        private val db = FirebaseFirestore.getInstance()
        private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"


        private val _uiState = MutableStateFlow<CommunityUiState>(CommunityUiState.Loading)
        val uiState: StateFlow<CommunityUiState> = _uiState

        init {
            fetchPosts()
        }

        fun fetchPosts() {
            _uiState.value = CommunityUiState.Loading

            db.collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        _uiState.value = CommunityUiState.Error(error.message ?: "Unexpected error")
                        return@addSnapshotListener
                    }

                    val posts = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(CommunityPost::class.java)?.copy(id = doc.id)
                    } ?: emptyList()
                    Log.d("CommunityViewModel", "Fetched posts: ${posts.size}")


                    _uiState.value = CommunityUiState.Success(posts)
                }
        }

        fun toggleLike(post: CommunityPost) {
            val postRef = db.collection("posts").document(post.id)
            val updatedLikes = if (currentUserId in post.likedBy) {
                post.likedBy - currentUserId
            } else {
                post.likedBy + currentUserId
            }

            postRef.update("likedBy", updatedLikes)
        }

        fun addComment(post: CommunityPost, message: String) {
            val userRef = db.collection("users").document(currentUserId)

            userRef.get()
                .addOnSuccessListener { document ->
                    val userName = document.getString("name") ?: "Anonymous"

                    val newComment = Comment(
                        userId = userName,  // 👈 Use name here
                        message = message,
                        timestamp = System.currentTimeMillis()
                    )

                    val commentMap = mapOf(
                        "userId" to newComment.userId,
                        "message" to newComment.message,
                        "timestamp" to newComment.timestamp
                    )

                    val postRef = db.collection("posts").document(post.id)
                    postRef.update("comments", com.google.firebase.firestore.FieldValue.arrayUnion(commentMap))
                        .addOnSuccessListener {
                            Log.d("CommunityViewModel", "Comment added with name")
                            fetchPosts()
                        }
                        .addOnFailureListener { e ->
                            Log.e("CommunityViewModel", "Failed to add comment", e)
                        }

                }
                .addOnFailureListener {
                    Log.e("CommunityViewModel", "Failed to fetch user name", it)
                }
        }

    }

    sealed class CommunityUiState {
        object Loading : CommunityUiState()
        data class Success(val posts: List<CommunityPost>) : CommunityUiState()
        data class Error(val message: String) : CommunityUiState()
    }


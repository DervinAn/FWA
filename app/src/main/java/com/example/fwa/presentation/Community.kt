package com.example.fwa.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fwp.CommunityPost
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CommunityFeedScreen(
    navController: NavController,
    viewModel: CommunityViewModel = viewModel()
) {
        val state by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        when (state) {
            is CommunityUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CommunityUiState.Error -> {
                val message = (state as CommunityUiState.Error).message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("❌ $message")
                }
            }

            is CommunityUiState.Success -> {
                val posts = (state as CommunityUiState.Success).posts
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF6F6F6))
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    items(posts.size) { post ->
                        CommunityPostItem(
                            post = posts[post],
                            onLike = { viewModel.toggleLike(posts[post]) },
                            onAddComment = { commentText -> viewModel.addComment(posts[post], commentText) }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun CommunityPostItem(
    post: CommunityPost,
    onLike: () -> Unit,
    onAddComment: (String) -> Unit
) {
    var commentText by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = post.userId,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = post.content)

            if (post.imageUrl.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = post.imageUrl,
                    contentDescription = "Post Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onLike) {
                    Icon(
                        imageVector = if (post.likedBy.contains("yourUserId")) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (post.likedBy.contains("yourUserId")) Color.Red else Color.Gray
                    )
                }

                Text(text = "${post.likedBy.size} likes")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = commentText,
                onValueChange = { commentText = it },
                placeholder = { Text("Write a comment...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (commentText.isNotBlank()) {
                        onAddComment(commentText)
                        commentText = ""
                    }
                },
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Post")
            }

            if (post.comments.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Text("Comments:", style = MaterialTheme.typography.titleSmall)
                post.comments.forEach { comment ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${comment.userId}: ${comment.message}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}



fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

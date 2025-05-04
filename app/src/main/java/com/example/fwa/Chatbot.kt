package com.example.fwa
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fwa.entities.ChatMessage
import com.example.fwa.ui.theme.TextColor

//@Composable
//fun ChatBotScreen(viewModel: ChatViewModel = viewModel()) {
//    var textChat by remember { mutableStateOf("") }
//    val chatMessages by remember { derivedStateOf { viewModel.chatMessages.reversed() } }
//
////    val chatMessages = listOf(
////        ChatMessage(text = "Hi there!", isUser = false),
////        ChatMessage(text = "Hello! How can I help you?", isUser = true),
////        ChatMessage(text = "Tell me a joke.", isUser = false),
////        ChatMessage(text = "Why don’t scientists trust atoms? Because they make up everything!", isUser = true)
////    )
//    Scaffold(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White),
//        bottomBar = { BottomNavigationBar() }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.fillMaxHeight(0.16f))
//
//            Image(
//                painter = painterResource(id = R.drawable.ai_bot),
//                contentDescription = null
//            )
//
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text("Hello!", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = TextColor)
//                Text("What can I do for you?", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = TextColor)
//            }
//
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//                    .padding(horizontal = 16.dp),
//                reverseLayout = true
//            ) {
//                items(chatMessages.size, key = { chatMessages[it].id }) { message ->
//                    ChatBubble(chatMessages[message])
//                }
//            }
//
//            OutlinedTextField(
//                value = textChat,
//                onValueChange = { textChat = it },
//                label = { Text("Type a message") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                shape = RoundedCornerShape(12.dp),
//                trailingIcon = {
//                    IconButton(onClick = {
//                        viewModel.sendUserMessage(textChat)
//                        textChat = ""
//                    }) {
//                        Icon(Icons.Default.Send, contentDescription = "Send")
//                    }
//                }
//            )
//        }
//    }
//}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isUser) Color(0xFF4CAF50) else Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            Text(text = message.text, color = Color.White)
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun ChatBotPreview() {
//    ChatBotScreen()
//}
package com.example.fwa

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fwa.ui.theme.AppBackground
import com.example.fwa.ui.theme.BlueGradient
import com.example.fwa.ui.theme.PeachGradient
import com.example.fwa.ui.theme.TextColor

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .safeContentPadding(),
        bottomBar = {
            BottomNavigationBar()
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                26.dp,
                alignment = Alignment.CenterVertically
            )
        ) {

            Text(
                "Welcome",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )
            Text("How i can assist you today?", fontSize = 20.sp)
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
            ) {
                AppButton("Ai Bot", R.drawable.chat, BlueGradient,
                    onClick = {
                        navController.navigate(Screen.Chat.route)
                    })
                AppButton("Community", R.drawable.handshake, PeachGradient,
                    onClick = {
                        navController.navigate(Screen.Community.route)
                    })
                AppButton("Recipes", R.drawable.recipe, BlueGradient,
                    onClick = {
                        navController.navigate(Screen.Recipes.route)
                    })
            }


        }
    }


}


@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { /* Handle click */ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home_agreement),
                    contentDescription = "Home",
                    modifier = Modifier.size(25.dp)
                )
            },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* Handle click */ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.chatbot),
                    contentDescription = "Search",
                    modifier = Modifier.size(38.dp)
                )
            },
            label = { Text("Chat") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* Handle click */ },
            icon = {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = "Profile"
                )
            },
            label = { Text("Community") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { /* Handle click */ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.cooking),
                    contentDescription = "Profile",
                    modifier = Modifier.size(30.dp)
                )
            },
            label = { Text("Recipes") }
        )
    }
}

@Composable
fun NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
) {
    //    val iconColor = if (selected) MaterialTheme.colorScheme.primary else LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
//    val labelColor = if (selected) MaterialTheme.colorScheme.primary else LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
//
    Column(
        modifier = Modifier
            .padding(8.dp)
            .then(Modifier.clickable(onClick = onClick)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon()
        }

        label()
    }
}

@Composable
fun AppButton(name: String, icon: Int, gradient: Brush,onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp, alignment = Alignment.Start)
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(45.dp)
                )
                Text(
                    name,
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}


@Preview
@Composable
private fun MainPreview() {
}
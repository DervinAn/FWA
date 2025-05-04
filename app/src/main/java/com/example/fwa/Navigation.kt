package com.example.fwa

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


sealed class Screen(val route:String){
    object Home : Screen("home")
    object Community : Screen("community")
    object Chat: Screen("chat")
    object Recipes : Screen("recipes")
    object Authen : Screen("auth")
}
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun NavScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Authen.route,
        modifier = modifier.safeContentPadding()
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.Chat.route) {
                val chatViewModel: ChatViewModel = viewModel()
                ChatPage(viewModel = chatViewModel)
        }

        composable(Screen.Community.route) {

        }
        composable(Screen.Authen.route) {
            AuthScreen(navController)
        }
        composable(Screen.Recipes.route) {


        }
    }
}

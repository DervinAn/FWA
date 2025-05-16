package com.example.fwa.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fwa.data.AuthRepositoryImpl
import com.example.fwa.data.RepoImpl.RecipeRepositoryImpl
import com.example.fwa.domaine.usecase.auth.GetInfoUseCase
import com.example.fwa.domaine.usecase.auth.SignInUseCase
import com.example.fwa.domaine.usecase.auth.SignUpUseCase
import com.example.fwa.domaine.usecase.recipe.AddRecipeUseCase
import com.example.fwa.domaine.usecase.recipe.DeleteRecipeUseCase
import com.example.fwa.domaine.usecase.recipe.GetAllRecipesUseCase
import com.example.fwa.domaine.usecase.recipe.GetRecipeByIdUseCase
import com.example.fwa.domaine.usecase.recipe.ModifyRecipeUseCase


sealed class Screen(val route:String){
    object Home : Screen("home")
    object Community : Screen("community")
    object Chat: Screen("chat")
    object Recipes : Screen("recipes")
    object Authen : Screen("auth")
    object RecipeDetail : Screen("recipeDetail/{recipeId}") {
        fun createRoute(recipeId: String) = "recipeDetail/$recipeId"
    }
}
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun NavScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val authRepository = AuthRepositoryImpl() // ✅ Create instance
    NavHost(
        navController = navController,
        startDestination = Screen.Authen.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.Chat.route) {
                val chatViewModel: ChatViewModel = viewModel()
//                ChatPage(viewModel = chatViewModel)
        }

        composable(Screen.Community.route) {
                CommunityFeedScreen(navController)
        }
        composable(Screen.Authen.route) {
            AuthScreen(navController,viewModel = AuthViewModel(
                authSi = SignInUseCase(authRepository),
                authSp = SignUpUseCase(authRepository),
                authGetInfo = GetInfoUseCase(authRepository)
            ),)
        }
        composable(Screen.Recipes.route) {
            RecipesScreen(navController, RecipeViewModel(
                getAll = GetAllRecipesUseCase(RecipeRepositoryImpl()),
                add = AddRecipeUseCase(RecipeRepositoryImpl()),
                delete = DeleteRecipeUseCase(RecipeRepositoryImpl()),
                modify = ModifyRecipeUseCase(RecipeRepositoryImpl()),
                getById = GetRecipeByIdUseCase(RecipeRepositoryImpl(),id = null),
            ))
        }
        composable("recipeDetail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")
            RecipeDetailScreen(
                recipeId = recipeId,
                viewModel = RecipeViewModel(
                    getAll = GetAllRecipesUseCase(RecipeRepositoryImpl()),
                    add = AddRecipeUseCase(RecipeRepositoryImpl()),
                    delete = DeleteRecipeUseCase(RecipeRepositoryImpl()),
                    modify = ModifyRecipeUseCase(RecipeRepositoryImpl()),
                    getById = GetRecipeByIdUseCase(RecipeRepositoryImpl(),id = recipeId),
                    ),
                navController = navController
            )
        }

    }
}

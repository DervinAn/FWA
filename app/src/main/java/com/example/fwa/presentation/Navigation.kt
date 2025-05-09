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
import com.example.fwp.Recipe


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
    val sampleRecipe = Recipe(
        id = "1",
        title = "Creamy Garlic Chicken",
        ingredients = listOf(
            "4 chicken breasts",
            "2 tbsp olive oil",
            "4 garlic cloves, minced",
            "1 cup heavy cream",
            "1/2 cup chicken broth",
            "Salt and pepper to taste",
            "Fresh parsley for garnish"
        ),
        steps = listOf(
            "Heat olive oil in a skillet over medium heat.",
            "Add chicken breasts and cook until golden and cooked through. Remove and set aside.",
            "In the same pan, sauté minced garlic until fragrant.",
            "Pour in chicken broth and heavy cream. Stir and let it simmer for 5 minutes.",
            "Return the chicken to the pan and coat with the creamy sauce.",
            "Simmer for another 5 minutes until everything is well combined.",
            "Serve hot and garnish with fresh parsley."
        ),
        imageUrl = "https://images.unsplash.com/photo-1605479729273-26d8d0ed6fc3", // example Unsplash image
        postedBy = "Chef Olivia"
    )

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

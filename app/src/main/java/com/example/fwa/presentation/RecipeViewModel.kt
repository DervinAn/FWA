package com.example.fwa.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fwa.domaine.usecase.recipe.AddRecipeUseCase
import com.example.fwa.domaine.usecase.recipe.DeleteRecipeUseCase
import com.example.fwa.domaine.usecase.recipe.GetAllRecipesUseCase
import com.example.fwa.domaine.usecase.recipe.GetRecipeByIdUseCase
import com.example.fwa.domaine.usecase.recipe.ModifyRecipeUseCase
import com.example.fwp.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val getAll: GetAllRecipesUseCase,
    private val add: AddRecipeUseCase,
    private val delete: DeleteRecipeUseCase,
    private val modify: ModifyRecipeUseCase,
    private val getById: GetRecipeByIdUseCase
) : ViewModel() {

    private val TAG = "RecipeViewModel"

    private val _uiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val uiState: StateFlow<RecipeUiState> = _uiState



    init {
        Log.d(TAG, "ViewModel initialized")
        fetchRecipes()
    }
    fun getRecipeById(id: String) {
        viewModelScope.launch {
            _uiState.value = RecipeUiState.Loading
            val result = getById(id)  // Assuming getById is the UseCase method that fetches data
            result.fold(
                onSuccess = { recipe ->
                    recipe?.let {
                        _uiState.value = RecipeUiState.Success(listOf(it))
                    } ?: run {
                        _uiState.value = RecipeUiState.Error("Recipe not found")
                    }
                },
                onFailure = {
                    _uiState.value = RecipeUiState.Error("Error: ${it.message}")
                }
            )
        }
    }


    fun fetchRecipes() {
        Log.d(TAG, "Fetching recipes...")
        viewModelScope.launch {
            _uiState.value = RecipeUiState.Loading
            val result = getAll()
            result.fold(
                onSuccess = {
                    Log.d(TAG, "Successfully fetched ${it.size} recipes")
                    _uiState.value = RecipeUiState.Success(it)
                },
                onFailure = {
                    Log.e(TAG, "Error fetching recipes: ${it.message}")
                    _uiState.value = RecipeUiState.Error(it.message ?: "Unknown error")
                }
            )
        }
    }

    fun addRecipe(recipe: Recipe) {
        Log.d(TAG, "Adding recipe: $recipe")
        viewModelScope.launch {
            add(recipe)
            Log.d(TAG, "Recipe added. Refreshing list...")
            fetchRecipes()
        }
    }

    fun deleteRecipe(id: String) {
        Log.d(TAG, "Deleting recipe with ID: $id")
        viewModelScope.launch {
            delete(id)
            Log.d(TAG, "Recipe deleted. Refreshing list...")
            fetchRecipes()
        }
    }

    fun modifyRecipe(recipe: Recipe) {
        Log.d(TAG, "Modifying recipe: $recipe")
        viewModelScope.launch {
            modify(recipe)
            Log.d(TAG, "Recipe modified. Refreshing list...")
            fetchRecipes()
        }
    }
}

sealed class RecipeUiState {
    object Loading : RecipeUiState()
    data class Success(val recipes: List<Recipe>) : RecipeUiState()
    data class Error(val message: String) : RecipeUiState()
}

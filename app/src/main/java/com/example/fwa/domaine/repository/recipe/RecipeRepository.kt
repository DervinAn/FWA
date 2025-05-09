package com.example.fwa.domaine.repository.recipe

import com.example.fwp.Recipe

interface RecipeRepository {
    suspend fun addRecipe(recipe: Recipe): Result<Boolean>
    suspend fun deleteRecipe(recipeId: String): Result<Boolean>
    suspend fun modifyRecipe(recipe: Recipe): Result<Boolean>
    suspend fun getAllRecipes(): Result<List<Recipe>>
    suspend fun getRecipeById(id : String): Result<Recipe>
}

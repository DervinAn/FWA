package com.example.fwa.domaine.usecase.recipe

import com.example.fwa.domaine.repository.recipe.RecipeRepository
import com.example.fwp.Recipe

class AddRecipeUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke(recipe: Recipe) = repo.addRecipe(recipe)
}

class DeleteRecipeUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke(recipeId: String) = repo.deleteRecipe(recipeId)
}

class ModifyRecipeUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke(recipe: Recipe) = repo.modifyRecipe(recipe)
}

class GetAllRecipesUseCase(private val repo: RecipeRepository) {
    suspend operator fun invoke() = repo.getAllRecipes()
}

class GetRecipeByIdUseCase(private val repo: RecipeRepository,id: String?){
    suspend operator fun invoke(id: String) = repo.getRecipeById(id)
}

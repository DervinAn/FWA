package com.example.fwa.data.RepoImpl

import com.example.fwa.domaine.repository.recipe.RecipeRepository
import com.example.fwp.Recipe
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class RecipeRepositoryImpl : RecipeRepository {
    private val db = Firebase.firestore.collection("recipes")

    override suspend fun addRecipe(recipe: Recipe): Result<Boolean> = try {
        db.document(recipe.id).set(recipe).await()
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteRecipe(recipeId: String): Result<Boolean> = try {
        db.document(recipeId).delete().await()
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun modifyRecipe(recipe: Recipe): Result<Boolean> = try {
        db.document(recipe.id).set(recipe).await()
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAllRecipes(): Result<List<Recipe>> = try {
        val snapshot = db.get().await()
        val recipes = snapshot.documents.mapNotNull { doc ->
            doc.toObject(Recipe::class.java)?.copy(id = doc.id)
        }
        Result.success(recipes)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getRecipeById(id: String): Result<Recipe> = try {
        val docSnapshot = db.document(id).get().await()

        if (docSnapshot.exists()) {
            val recipe = docSnapshot.toObject(Recipe::class.java)
            Result.success(recipe)
        } else {
            Result.success(null)
        }
    } catch (e: Exception) {
        Result.failure(e)
    } as Result<Recipe>

}

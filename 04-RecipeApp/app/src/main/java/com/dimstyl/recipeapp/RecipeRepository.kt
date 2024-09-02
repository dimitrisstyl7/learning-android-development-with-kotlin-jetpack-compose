package com.dimstyl.recipeapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(private val apiService: ApiService) {

    suspend fun fetchCategories(): Result<CategoriesResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCategories()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}
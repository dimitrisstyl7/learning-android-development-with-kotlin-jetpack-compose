package com.dimstyl.recipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    init {
        fetchCategories()
    }

    private val _categoriesState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoriesState

    private fun fetchCategories() {
        viewModelScope.launch {
            val result = repository.fetchCategories();

            if (result.isSuccess) {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    list = result.getOrNull()?.categories ?: emptyList(),
                    error = null
                )
            } else {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error fetching categories.\n${result.exceptionOrNull()?.message}"
                )
            }
        }
    }

    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )

}
package com.example.composerecipeapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipeapp.api.RecipeApi
import com.example.composerecipeapp.helper.util.FoodCategory
import com.example.composerecipeapp.helper.util.getFoodCategory
import com.example.composerecipeapp.model.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
) : ViewModel() {
    companion object {
        const val PAGE_SIZE = 30
    }

    val recipes: MutableState<List<RecipeModel>> = mutableStateOf(listOf())

    val query: MutableState<String> = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    val loading: MutableState<Boolean> = mutableStateOf(false)

    val page: MutableState<Int> = mutableStateOf(1)

    private var recipeScrollPosition = 0

    init {
        search()
    }

    fun search() {
        viewModelScope.launch {
            loading.value = true
            resetSearchState()
            delay(3000)
            recipes.value = repository.search(1, query.value, RecipeApi.TOKEN)
            loading.value = false
        }
    }

    fun nextPage() {
        viewModelScope.launch {
            // prevent duplicate events due to recompose happening too quickly
            if ((recipeScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                loading.value = true
                incrementPage()

                if (page.value > 1) {
                    appendRecipes(
                        repository.search(
                            page = page.value,
                            query = query.value,
                            token = RecipeApi.TOKEN
                        )
                    )
                }

                loading.value = false
            }
        }
    }

    fun onQueryChange(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChange(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChange(category)
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeScrollPosition = position
    }

    /**
     * Append new recipes new page
     */
    private fun appendRecipes(recipes: List<RecipeModel>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }
}
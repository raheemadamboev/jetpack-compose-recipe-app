package com.example.composerecipeapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
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
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        const val PAGE_SIZE = 30

        const val STATE_KEY_PAGE = "recipe.state.page.key"
        const val STATE_KEY_QUERY = "recipe.state.query.key"
        const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
        const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"
    }

    val recipes: MutableState<List<RecipeModel>> = mutableStateOf(listOf())

    val query: MutableState<String> = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    val loading: MutableState<Boolean> = mutableStateOf(false)

    val page: MutableState<Int> = mutableStateOf(1)

    private var recipeScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { setPage(it) }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { setQuery(it) }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { setListScrollPosition(it) }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { setSelectedFoodCategory(it) }

        if (recipeScrollPosition != 0) {
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    RecipeListEvent.NewSearchEvent -> search()
                    RecipeListEvent.NextPageEvent -> nextPage()
                    RecipeListEvent.RestoreStateEvent -> restoreState()
                }
            } catch (e: Exception) {
                println("raheem: ${e.cause}")
            }
        }
    }

    private suspend fun search() {
        loading.value = true
        resetSearchState()
        delay(3000)
        recipes.value = repository.search(1, query.value, RecipeApi.TOKEN)
        loading.value = false
    }

    private suspend fun nextPage() {
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

    private suspend fun restoreState() {
        loading.value = true

        val results: MutableList<RecipeModel> = mutableListOf()

        for (i in 1..page.value) {
            val result = repository.search(i, query.value, RecipeApi.TOKEN)
            results.addAll(result)
        }

        recipes.value = results
        loading.value = false
    }

    fun onQueryChange(query: String) {
        setQuery(query)
    }

    fun onSelectedCategoryChange(category: String) {
        val newCategory = getFoodCategory(category)
        setSelectedFoodCategory(newCategory)
        onQueryChange(category)
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position)
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
        setSelectedFoodCategory(null)
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    private fun setListScrollPosition(position: Int) {
        recipeScrollPosition = position
        savedStateHandle[STATE_KEY_LIST_POSITION] = position
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle[STATE_KEY_PAGE] = page
    }

    private fun setSelectedFoodCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle[STATE_KEY_SELECTED_CATEGORY] = category
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle[STATE_KEY_QUERY] = query
    }
}
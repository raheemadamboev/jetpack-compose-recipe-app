package com.example.composerecipeapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipeapp.api.RecipeApi
import com.example.composerecipeapp.model.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
) : ViewModel() {

    val recipes: MutableState<List<RecipeModel>> = mutableStateOf(listOf())

    val query: MutableState<String> = mutableStateOf("")

    init {
        search(query.value)
    }

    fun search(query: String) {
        viewModelScope.launch {
            recipes.value = repository.search(1, query, RecipeApi.TOKEN)
        }
    }

    fun onQueryChange(query: String) {
        this.query.value = query
    }
}
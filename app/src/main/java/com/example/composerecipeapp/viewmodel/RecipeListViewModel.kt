package com.example.composerecipeapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.composerecipeapp.model.RecipeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
) : ViewModel() {

    val recipes: MutableState<List<RecipeModel>> = mutableStateOf(listOf())

}
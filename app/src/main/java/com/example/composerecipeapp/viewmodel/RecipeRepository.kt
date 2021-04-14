package com.example.composerecipeapp.viewmodel

import com.example.composerecipeapp.model.RecipeModel

interface RecipeRepository {

    suspend fun search(token: String, page: Int, query: String): List<RecipeModel>

    suspend fun get(token: String, id: Int): RecipeModel
}
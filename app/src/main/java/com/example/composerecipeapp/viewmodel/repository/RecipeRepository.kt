package com.example.composerecipeapp.viewmodel.repository

import com.example.composerecipeapp.model.RecipeModel

interface RecipeRepository {

    suspend fun search(page: Int, query: String, token: String): List<RecipeModel>

    suspend fun get(id: Int, token: String): RecipeModel
}
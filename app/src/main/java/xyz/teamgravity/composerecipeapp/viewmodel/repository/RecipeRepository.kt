package xyz.teamgravity.composerecipeapp.viewmodel.repository

import xyz.teamgravity.composerecipeapp.model.RecipeModel

interface RecipeRepository {

    suspend fun search(page: Int, query: String, token: String): List<RecipeModel>

    suspend fun get(id: Int, token: String): RecipeModel
}
package com.example.composerecipeapp.viewmodel

import com.example.composerecipeapp.api.RecipeApi
import com.example.composerecipeapp.helper.mapper.RecipeDtoMapper
import com.example.composerecipeapp.model.RecipeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<RecipeModel> {
        return mapper.toDomainList(api.search(token, page, query).recipes)
    }

    override suspend fun get(token: String, id: Int): RecipeModel {
        return mapper.mapToDomainModel(api.get(token, id))
    }
}
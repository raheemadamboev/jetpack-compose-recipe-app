package xyz.teamgravity.composerecipeapp.viewmodel.repository

import xyz.teamgravity.composerecipeapp.api.RecipeApi
import xyz.teamgravity.composerecipeapp.helper.mapper.RecipeDtoMapper
import xyz.teamgravity.composerecipeapp.model.RecipeModel

class RecipeRepositoryImpl(
    private val api: RecipeApi,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {

    override suspend fun search(page: Int, query: String, token: String): List<RecipeModel> {
        return mapper.toDomainList(api.search(page, query, token).recipes)
    }

    override suspend fun get(id: Int, token: String): RecipeModel {
        return mapper.mapToDomainModel(api.get(id, token))
    }
}
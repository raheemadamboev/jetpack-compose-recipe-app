package xyz.teamgravity.composerecipeapp.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import xyz.teamgravity.composerecipeapp.model.RecipeDtoModel
import xyz.teamgravity.composerecipeapp.model.SearchResponseModel

interface RecipeApi {
    companion object {
        const val BASE_URL = "https://food2fork.ca/api/recipe/"
        const val TOKEN = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }

    @GET("search")
    suspend fun search(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Header("Authorization") token: String
        ): SearchResponseModel

    @GET("get")
    suspend fun get(
        @Query("id") id: Int,
        @Header("Authorization") token: String
        ): RecipeDtoModel
}
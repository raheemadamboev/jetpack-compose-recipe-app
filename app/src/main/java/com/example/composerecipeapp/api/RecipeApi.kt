package com.example.composerecipeapp.api

import com.example.composerecipeapp.model.RecipeDtoModel
import com.example.composerecipeapp.model.SearchResponseModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeApi {
    companion object {
        const val BASE_URL = "https://food2fork.ca/api/recipe/"
        const val TOKEN = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }

    @GET("search")
    suspend fun search(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Header("Authorization") token: String = TOKEN
        ): SearchResponseModel

    @GET("get")
    suspend fun get(
        @Query("id") id: Int,
        @Header("Authorization") token: String = TOKEN
        ): RecipeDtoModel
}
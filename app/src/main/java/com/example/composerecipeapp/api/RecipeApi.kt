package com.example.composerecipeapp.api

import com.example.composerecipeapp.model.RecipeDtoModel
import com.example.composerecipeapp.model.SearchResponseModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeApi {

    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): SearchResponseModel

    @GET("get")
    suspend fun get(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): RecipeDtoModel
}
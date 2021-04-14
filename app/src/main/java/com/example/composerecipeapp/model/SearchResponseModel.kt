package com.example.composerecipeapp.model

import com.google.gson.annotations.SerializedName

data class SearchResponseModel(

    @SerializedName("count")
    val count: Int,

    @SerializedName("results")
    val recipes: List<RecipeDtoModel>
)

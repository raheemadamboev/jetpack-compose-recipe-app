package com.example.composerecipeapp.viewmodel

sealed class RecipeEvent {

    data class GetRecipeEvent(val id: Int) : RecipeEvent()
}
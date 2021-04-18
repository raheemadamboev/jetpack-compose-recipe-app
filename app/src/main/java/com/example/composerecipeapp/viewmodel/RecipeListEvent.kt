package com.example.composerecipeapp.viewmodel

sealed class RecipeListEvent {

    object NewSearchEvent : RecipeListEvent()

    object NextPageEvent : RecipeListEvent()

    // restore process death
    object RestoreStateEvent : RecipeListEvent()
}

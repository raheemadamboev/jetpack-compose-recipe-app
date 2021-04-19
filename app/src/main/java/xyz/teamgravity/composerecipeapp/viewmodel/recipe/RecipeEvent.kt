package xyz.teamgravity.composerecipeapp.viewmodel.recipe

sealed class RecipeEvent {

    data class GetRecipeEvent(val id: Int) : RecipeEvent()
}
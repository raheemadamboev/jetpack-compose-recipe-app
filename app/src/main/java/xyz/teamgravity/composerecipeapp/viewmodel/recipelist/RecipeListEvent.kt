package xyz.teamgravity.composerecipeapp.viewmodel.recipelist

sealed class RecipeListEvent {

    object NewSearchEvent : RecipeListEvent()

    object NextPageEvent : RecipeListEvent()

    // restore process death
    object RestoreStateEvent : RecipeListEvent()
}

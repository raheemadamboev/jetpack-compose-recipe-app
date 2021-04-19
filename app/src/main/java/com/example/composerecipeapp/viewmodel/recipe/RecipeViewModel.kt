package com.example.composerecipeapp.viewmodel.recipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composerecipeapp.api.RecipeApi
import com.example.composerecipeapp.model.RecipeModel
import com.example.composerecipeapp.viewmodel.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val STATE_KEY_RECIPE = "recipe.state.key.id"
    }

    val recipe: MutableState<RecipeModel?> = mutableStateOf(null)

    val loading: MutableState<Boolean> = mutableStateOf(false)

    init {
        stateHandle.get<RecipeModel>(STATE_KEY_RECIPE)?.let { recipe.value = it }
    }

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeEvent.GetRecipeEvent -> if (recipe.value == null) getRecipe(event.id)
                }
            } catch (e: Exception) {
                println("raheem: ${e.message}")
            }
        }
    }

    // get recipe from api
    private suspend fun getRecipe(id: Int) {
        loading.value = true

        // intentional delay to see amazing shimmer animation :)
        delay(1000)

        val recipe = repository.get(id, RecipeApi.TOKEN)
        this.recipe.value = recipe

        stateHandle[STATE_KEY_RECIPE] = recipe

        loading.value = false
    }
}
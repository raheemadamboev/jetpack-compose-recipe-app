package xyz.teamgravity.composerecipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import xyz.teamgravity.composerecipeapp.model.RecipeModel
import com.example.composerecipeapp.presentation.fragment.RecipeListFragmentDirections
import xyz.teamgravity.composerecipeapp.viewmodel.recipelist.RecipeListEvent
import xyz.teamgravity.composerecipeapp.viewmodel.recipelist.RecipeListViewModel

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<RecipeModel>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerEvent: (RecipeListEvent) -> Unit,
    scaffoldState: ScaffoldState,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (loading && recipes.isEmpty()) {
            LoadingRecipeListShimmer(imageHeight = 250.dp)
        } else {
            LazyColumn {
                itemsIndexed(items = recipes) { index, recipe ->
                    onChangeRecipeScrollPosition(index)

                    if ((index + 1) >= (page * RecipeListViewModel.PAGE_SIZE) && !loading) {
                        onTriggerEvent(RecipeListEvent.NextPageEvent)
                    }

                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            navController.navigate(RecipeListFragmentDirections.actionRecipeListFragmentToRecipeFragment(recipe.id ?: -1))
                        })
                }
            }
        }

        CircularIndeterminateProgressBar(isDisplayed = loading)
        DefaultSnackbar(
            snackbarHostState = scaffoldState.snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        }
    }
}
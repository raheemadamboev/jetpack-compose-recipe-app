package com.example.composerecipeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.composerecipeapp.injection.App
import com.example.composerecipeapp.viewmodel.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewmodel by viewModels<RecipeListViewModel>()

    @Inject
    lateinit var application: App

    @ExperimentalComposeUiApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {

                //    val isShowing = remember { mutableStateOf(false) }

                val snackbarHostState = remember { SnackbarHostState() }

                Column {
                    Button(onClick = {
                        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                            snackbarHostState.showSnackbar(
                                message = "hey look snackbar",
                                actionLabel = "Show",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }) {
                        Text(text = "Show snackbar")
                    }
                    DecoupledSnackbarDemo(snackbarHostState = snackbarHostState)
                }

                /*
               AppTheme(darkTheme = application.isDark.value) {
                   val recipes = viewmodel.recipes.value

                   val query = viewmodel.query.value

                   val keyboard = LocalSoftwareKeyboardController.current

                   val selectedCategory = viewmodel.selectedCategory.value

                   val loading = viewmodel.loading.value

                   Scaffold(
                       topBar = {
                           SearchAppBar(
                               query = query,
                               onQueryChange = viewmodel::onQueryChange,
                               onSearch = viewmodel::search,
                               categories = getAllFoodCategories(),
                               keyboard = keyboard,
                               selectedCategory = selectedCategory,
                               onSelectedCategoryChange = viewmodel::onSelectedCategoryChange,
                               onToggleTheme = {
                                   application.toggleLightTheme()
                               }
                           )
                       },
                   ) {
                       Box(
                           modifier = Modifier
                               .fillMaxSize()
                               .background(MaterialTheme.colors.background)
                       ) {
                           if (loading) {
                               LoadingRecipeListShimmer(imageHeight = 250.dp)
                           } else {
                               LazyColumn {
                                   itemsIndexed(
                                       items = recipes
                                   ) { _, item ->
                                       RecipeCard(recipe = item, onClick = {})
                                   }
                               }
                           }

                           CircularIndeterminateProgressBar(isDisplayed = loading)
                       }
                   }
               }
                    */
            }
        }
    }
}

/*@Composable
fun GradientDemo() {
    val colors = listOf(
        Color.Blue,
        Color.Red,
        Color.Blue
    )

    val brush = Brush.linearGradient(
        colors,
        start = Offset(200f, 200f),
        end = Offset(400f, 400f)
    )

    Surface(shape = MaterialTheme.shapes.small) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
        )
    }
}
 */

@Composable
fun DecoupledSnackbarDemo(
    snackbarHostState: SnackbarHostState
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackbar = createRef()

        SnackbarHost(
            modifier = Modifier.constrainAs(snackbar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(action = {
                    TextButton(
                        onClick = { snackbarHostState.currentSnackbarData?.dismiss() }
                    ) {
                        Text(
                            text = snackbarHostState.currentSnackbarData?.actionLabel ?: "",
                            style = MaterialTheme.typography.button
                        )
                    }
                }) {
                    Text(text = snackbarHostState.currentSnackbarData?.message ?: "")
                }
            }
        )
    }
}

@Composable
fun SnackBarDemo(isShowing: Boolean, onHideSnackbar: () -> Unit) {
    if (isShowing) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val snackbar = createRef()

            Snackbar(
                modifier = Modifier.constrainAs(snackbar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                action = {
                    Text(
                        text = "Hide",
                        modifier = Modifier.clickable(onClick = onHideSnackbar),
                        style = MaterialTheme.typography.h5
                    )
                })
            {
                Text(text = "Hey look snackbar")
            }
        }
    }
}
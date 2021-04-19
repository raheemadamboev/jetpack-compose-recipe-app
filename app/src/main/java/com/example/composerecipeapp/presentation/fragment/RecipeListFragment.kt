package com.example.composerecipeapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.composerecipeapp.helper.util.getAllFoodCategories
import com.example.composerecipeapp.injection.App
import com.example.composerecipeapp.presentation.components.RecipeList
import com.example.composerecipeapp.presentation.components.SearchAppBar
import com.example.composerecipeapp.presentation.theme.AppTheme
import com.example.composerecipeapp.presentation.util.SnackbarController
import com.example.composerecipeapp.viewmodel.recipelist.RecipeListEvent
import com.example.composerecipeapp.viewmodel.recipelist.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: App

    private val viewmodel by viewModels<RecipeListViewModel>()

    private val snackbarController = SnackbarController(lifecycleScope)

    @ExperimentalComposeUiApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(darkTheme = application.isDark.value) {
                    val recipes = viewmodel.recipes.value

                    val query = viewmodel.query.value

                    val selectedCategory = viewmodel.selectedCategory.value

                    val loading = viewmodel.loading.value

                    val page = viewmodel.page.value

                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChange = viewmodel::onQueryChange,
                                onSearch = {
                                    // to only demonstrate snackbar
                                    if (viewmodel.selectedCategory.value?.value == "Milk") {
                                        snackbarController.showSnackbar(
                                            scaffoldState = scaffoldState,
                                            message = "Invalid category",
                                            actionLabel = "Hide"
                                        )
                                    } else {
                                        viewmodel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                    }
                                },
                                categories = getAllFoodCategories(),
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChange = viewmodel::onSelectedCategoryChange,
                                onToggleTheme = {
                                    application.toggleLightTheme()
                                }
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            onChangeRecipeScrollPosition = viewmodel::onChangeRecipeScrollPosition,
                            page = page,
                            onTriggerEvent = viewmodel::onTriggerEvent,
                            scaffoldState = scaffoldState,
                            navController = findNavController()
                        )
                    }
                }
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

/*
@Composable
fun SnackbarBody() {

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
}
 */

/*
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
 */
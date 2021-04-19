package xyz.teamgravity.composerecipeapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import xyz.teamgravity.composerecipeapp.injection.App
import xyz.teamgravity.composerecipeapp.presentation.components.CircularIndeterminateProgressBar
import xyz.teamgravity.composerecipeapp.presentation.components.LoadingRecipeShimmer
import xyz.teamgravity.composerecipeapp.presentation.components.RecipeView
import xyz.teamgravity.composerecipeapp.presentation.theme.AppTheme
import xyz.teamgravity.composerecipeapp.viewmodel.recipe.RecipeEvent
import xyz.teamgravity.composerecipeapp.viewmodel.recipe.RecipeViewModel
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private val args by navArgs<RecipeFragmentArgs>()

    private val viewModel by viewModels<RecipeViewModel>()

    @Inject
    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(args.recipeId))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipe = viewModel.recipe.value

                val loading = viewModel.loading.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(darkTheme = app.isDark.value) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (loading) {
                                LoadingRecipeShimmer(imageHeight = 260.dp)
                            } else {
                                recipe?.let {
                                    RecipeView(recipe = it)
                                }
                            }

                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }
                    }
                }
            }
        }
    }
}
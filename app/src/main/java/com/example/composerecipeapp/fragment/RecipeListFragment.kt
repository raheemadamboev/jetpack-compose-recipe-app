package com.example.composerecipeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composerecipeapp.helper.util.getAllFoodCategories
import com.example.composerecipeapp.injection.App
import com.example.composerecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.example.composerecipeapp.presentation.components.RecipeCard
import com.example.composerecipeapp.presentation.components.SearchAppBar
import com.example.composerecipeapp.presentation.theme.AppTheme
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
                AppTheme(darkTheme = application.isDark.value) {
                    val recipes = viewmodel.recipes.value

                    val query = viewmodel.query.value

                    val keyboard = LocalSoftwareKeyboardController.current

                    val selectedCategory = viewmodel.selectedCategory.value

                    val loading = viewmodel.loading.value

                    Column {
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

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.background)
                        ) {
                            LazyColumn {
                                itemsIndexed(
                                    items = recipes
                                ) { _, item ->
                                    RecipeCard(recipe = item, onClick = {})
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

@Composable
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
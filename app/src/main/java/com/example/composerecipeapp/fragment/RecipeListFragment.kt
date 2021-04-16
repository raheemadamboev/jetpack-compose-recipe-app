package com.example.composerecipeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composerecipeapp.components.CircularIndeterminateProgressBar
import com.example.composerecipeapp.components.RecipeCard
import com.example.composerecipeapp.components.SearchAppBar
import com.example.composerecipeapp.viewmodel.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewmodel by viewModels<RecipeListViewModel>()

    @ExperimentalComposeUiApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {

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
                        keyboard = keyboard,
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChange = viewmodel::onSelectedCategoryChange,
                        onChangeCategoryScrollPosition = viewmodel::onChangeCategoryScrollPosition
                    )

                    Box(modifier = Modifier.fillMaxSize()) {
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
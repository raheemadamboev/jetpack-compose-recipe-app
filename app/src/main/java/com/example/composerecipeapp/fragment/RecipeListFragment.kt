package com.example.composerecipeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composerecipeapp.components.FoodCategoryChip
import com.example.composerecipeapp.components.RecipeCard
import com.example.composerecipeapp.helper.util.getAllFoodCategories
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

                Column {
                    // build toolbar
                    Surface(
                        elevation = 8.dp,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White
                    ) {
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                TextField(
                                    value = query,
                                    onValueChange = { viewmodel.onQueryChange(it) },
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(8.dp),
                                    label = { Text(text = "Search") },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Search
                                    ),
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.Search,
                                            contentDescription = "raheem"
                                        )
                                    },
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            viewmodel.search()
                                            keyboard?.hide()
                                        }
                                    ),
                                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = MaterialTheme.colors.surface
                                    )
                                )
                            }

                            val scrollState = rememberScrollState()

                            Row(
                                modifier = Modifier
                                    .horizontalScroll(state = scrollState)
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, bottom = 8.dp)
                            ) {

                                for (category in getAllFoodCategories()) {
                                    FoodCategoryChip(
                                        category = category.value,
                                        isSelected = selectedCategory == category,
                                        onExecuteSearch = viewmodel::search,
                                        onSelectedCategoryChanged = {
                                            viewmodel.onSelectedCategoryChange(it)
                                            viewmodel.onChangeCategoryScrollPosition(scrollState.value)
                                        }
                                    )
                                }
                            }
                        }
                    }

                    LazyColumn {
                        itemsIndexed(
                            items = recipes
                        ) { _, item ->
                            RecipeCard(recipe = item, onClick = {})
                        }
                    }
                }
            }
        }
    }
}
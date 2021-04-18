package com.example.composerecipeapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composerecipeapp.helper.util.FoodCategory

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    categories: List<FoodCategory>,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChange: (String) -> Unit,
    onToggleTheme: () -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current

    Surface(
        elevation = 8.dp,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = query,
                    onValueChange = { onQueryChange(it) },
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
                            onSearch()
                            keyboard?.hide()
                        }
                    ),
                    textStyle = MaterialTheme.typography.h5,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                    )
                )

                ConstraintLayout(modifier = Modifier.align(Alignment.CenterVertically)) {
                    val menu = createRef()
                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier.constrainAs(menu) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "raheem")
                    }
                }
            }

            val scrollState = rememberLazyListState()
            LazyRow(
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
                state = scrollState
            ) {
                items(categories) { category ->
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChange(it)
                        },
                        onExecuteSearch = { onSearch() },
                    )
                }
            }
        }
    }
}
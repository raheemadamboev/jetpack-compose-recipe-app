package com.example.composerecipeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composerecipeapp.components.RecipeCard
import com.example.composerecipeapp.viewmodel.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewmodel by viewModels<RecipeListViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewmodel.recipes.value

                LazyColumn {
                    itemsIndexed(
                        items = recipes
                    ) { index, item ->
                        RecipeCard(recipe = item, onClick = {})
                    }
                }
            }
        }
    }
}
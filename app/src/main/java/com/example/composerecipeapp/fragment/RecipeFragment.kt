package com.example.composerecipeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.composerecipeapp.viewmodel.RecipeEvent
import com.example.composerecipeapp.viewmodel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private val args by navArgs<RecipeFragmentArgs>()

    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(args.recipeId))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipe = viewModel.recipe.value

                val loading = viewModel.loading.value

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = (recipe?.title).toString(),
                        style = TextStyle(
                            fontSize = 21.sp
                        )
                    )
                }
            }
        }
    }
}
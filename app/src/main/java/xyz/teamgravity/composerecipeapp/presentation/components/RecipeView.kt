package xyz.teamgravity.composerecipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.composerecipeapp.R
import xyz.teamgravity.composerecipeapp.helper.util.loadPicture
import xyz.teamgravity.composerecipeapp.model.RecipeModel

@Composable
fun RecipeView(
    recipe: RecipeModel
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        recipe.featuredImage?.let {
            val image = loadPicture(url = it, defaultImageRes = R.drawable.empty_plate).value
            image?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "raheem",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            recipe.title?.let {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h3
                    )
                    Text(
                        text = recipe.rating.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.h5
                    )
                }
            }

            recipe.publisher?.let {
                Text(
                    text = if (recipe.dateUpdated != null) "Updated ${recipe.dateUpdated} by $it" else "By $it",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, top = 4.dp),
                    style = MaterialTheme.typography.caption
                )
            }

            for (ingredient in recipe.ingredients) {
                Text(
                    text = ingredient, modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp), style = MaterialTheme.typography.body1
                )
            }
        }
    }
}
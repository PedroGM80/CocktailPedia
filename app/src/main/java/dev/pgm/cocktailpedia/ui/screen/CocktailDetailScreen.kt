package dev.pgm.cocktailpedia.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.pgm.domain.Cocktail

@Composable
fun CocktailDetailScreen(cocktail: Cocktail, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = cocktail.strDrinkThumb,
            contentDescription = cocktail.strDrink,
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Text(text = cocktail.strDrink)
        Text(
            text = "Category: ${cocktail.strCategory ?: "N/A"}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Type: ${cocktail.strAlcoholic ?: "N/A"}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Glass: ${cocktail.strGlass ?: "N/A"}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Ingredients:",
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(vertical = 8.dp)
        )

        cocktail.getIngredientsList().zip(cocktail.getMeasuresList())
            .forEach { (ingredient, measure) ->
                Text("• $measure $ingredient")
            }

        Text(
            text = "Instructions:",
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(vertical = 8.dp)
        )

        Text(text = cocktail.strInstructions)
    }
}
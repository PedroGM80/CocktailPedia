package dev.pgm.cocktailpedia.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.pgm.domain.Cocktail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetailScreen(cocktail: Cocktail, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(cocktail.strDrink, style = MaterialTheme.typography.headlineSmall) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                AsyncImage(
                    model = cocktail.strDrinkThumb,
                    contentDescription = cocktail.strDrink,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = cocktail.strDrink,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                        MetaDataItem(label = "Category", value = cocktail.strCategory)
                        Spacer(Modifier.width(16.dp))
                        MetaDataItem(label = "Glass", value = cocktail.strGlass)
                    }
                }
            }

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier.padding(16.dp)) {
                    Text(
                        text = "Ingredients:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    cocktail.getIngredientsList().zip(cocktail.getMeasuresList()) { ingredient, measure ->
                        Row {
                            Text("• $measure", style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.width(4.dp))
                            Text(ingredient, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            Card(
                modifier = modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier.padding(16.dp)) {
                    Text(
                        text = "Instructions:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = cocktail.strInstructions, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun MetaDataItem(label: String, value: String?) {
    Column {
        Text(label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        Text(value ?: "N/A", style = MaterialTheme.typography.bodyMedium)
    }
}
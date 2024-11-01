package dev.pgm.cocktailpedia.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.pgm.cocktailpedia.R
import dev.pgm.cocktailpedia.models.Cocktail

@Composable
fun CocktailListScreen(
    cocktails: List<Cocktail>,
    isLoading: Boolean,
    onCocktailClick: (Cocktail) -> Unit,
    onLetterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = stringResource(R.string.cocktailpedia),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier.padding(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 48.dp),
            modifier = modifier.padding(8.dp)
        ) {
            items(('A'..'Z').toList()) { letter ->
                TextButton(
                    onClick = { onLetterSelected(letter.toString()) },
                    modifier = Modifier
                        .padding(4.dp)
                        .wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = letter.toString(),
                        fontSize = 16.sp,
                        modifier = modifier.padding(4.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        if (isLoading) {
            Box(modifier = modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier.fillMaxSize()
            ) {
                items(cocktails) { cocktail ->
                    CocktailCard(
                        cocktail = cocktail,
                        onClick = { onCocktailClick(cocktail) }
                    )
                }
            }
        }
    }
}
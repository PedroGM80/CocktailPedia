package dev.pgm.cocktailpedia.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.pgm.cocktailpedia.R
import dev.pgm.domain.Cocktail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailListScreen(
    cocktails: List<Cocktail>,
    isLoading: Boolean,
    onCocktailClick: (Cocktail) -> Unit,
    onLetterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        TopAppBar(
            modifier = modifier.height(80.dp),
            title = {
                Row(
                    modifier = modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = stringResource(R.string.cocktailpedia),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically // Centrar verticalmente
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 48.dp),
                    state = gridState,
                    modifier = Modifier.weight(1f), // La grilla ocupa el espacio disponible
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(('A'..'Z').toList()) { letter ->
                        LetterFilterChip(
                            letter = letter,
                            onLetterSelected = onLetterSelected,
                            scope = scope,
                            gridState = gridState
                        )
                    }
                }
            }

            when {
                isLoading -> LoadingIndicator()
                cocktails.isEmpty() -> EmptyCocktailList()
                else -> CocktailGrid(cocktails, onCocktailClick)
            }
        }
    }
}

@Composable
fun LetterFilterChip(
    letter: Char,
    onLetterSelected: (String) -> Unit,
    scope: CoroutineScope,
    gridState: LazyGridState,
) {
    FilterChip(selected = false, onClick = {
        onLetterSelected(letter.toString())
        scope.launch {
            gridState.scrollToItem(0)
        }
    }, label = {
        Text(
            text = letter.toString(), fontSize = 16.sp, textAlign = TextAlign.Center
        )
    }, shape = RoundedCornerShape(8.dp), colors = FilterChipDefaults.filterChipColors(
        containerColor = MaterialTheme.colorScheme.primary,
        labelColor = MaterialTheme.colorScheme.onPrimary
    ), modifier = Modifier.padding(2.dp)
    )
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun EmptyCocktailList() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.no_cocktails_available),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center) // Center the text
                .padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun CocktailGrid(cocktails: List<Cocktail>, onCocktailClick: (Cocktail) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(cocktails) { cocktail ->
            CocktailCard(cocktail = cocktail, onClick = { onCocktailClick(cocktail) })
        }
    }
}
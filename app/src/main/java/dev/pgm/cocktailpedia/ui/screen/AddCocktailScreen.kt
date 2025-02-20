package dev.pgm.cocktailpedia.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCocktailScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Añadir Cocktail") }) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text("Aquí van los detalles para añadir un nuevo cocktail")
        }
    }
}

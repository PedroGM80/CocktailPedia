package dev.pgm.cocktailpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.pgm.cocktailpedia.ui.cocktailUseCase
import dev.pgm.cocktailpedia.ui.screen.navigation.HostNavigation
import dev.pgm.cocktailpedia.ui.theme.CocktailPediaTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel =   CocktailViewModel(cocktailUseCase)
        enableEdgeToEdge()
        setContent {
            CocktailPediaTheme { HostNavigation(viewModel) }
        }
    }
}
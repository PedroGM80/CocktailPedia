package dev.pgm.cocktailpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dev.pgm.cocktailpedia.ui.screen.navigation.HostNavigation
import dev.pgm.cocktailpedia.ui.theme.CocktailPediaTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: CocktailViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            CocktailPediaTheme { HostNavigation(viewModel) }
        }
    }
}
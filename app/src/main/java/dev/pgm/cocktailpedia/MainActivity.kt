package dev.pgm.cocktailpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.pgm.cocktailpedia.ui.screen.AddCocktailViewModel
import dev.pgm.cocktailpedia.ui.screen.CocktailViewModel
import dev.pgm.cocktailpedia.ui.screen.navigation.Host
import dev.pgm.cocktailpedia.ui.theme.CocktailPediaTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cocktailViewModel: CocktailViewModel = getViewModel()
        val addCocktailViewModel: AddCocktailViewModel = getViewModel()
        enableEdgeToEdge()
        setContent {
            CocktailPediaTheme {
                Host(
                    viewModel = cocktailViewModel,
                    addCocktailViewModel = addCocktailViewModel,
                )
            }
        }
    }
}
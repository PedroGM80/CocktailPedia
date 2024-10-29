package dev.pgm.cocktailpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.pgm.cocktailpedia.ui.screen.navigation.HostNavigation
import dev.pgm.cocktailpedia.ui.theme.CocktailPediaTheme
import dev.pgm.data.CocktailRepository
import dev.pgm.usecases.GetCocktailUseCase


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cocktailServerDataSource = CocktailServerDataSource()
        val cocktailRepository = CocktailRepository(cocktailServerDataSource)
        val cocktailUseCase = GetCocktailUseCase(cocktailRepository)
        val viewModel =   CocktailViewModel(cocktailUseCase)
        enableEdgeToEdge()
        setContent {
            CocktailPediaTheme { HostNavigation(viewModel) }
        }
    }
}
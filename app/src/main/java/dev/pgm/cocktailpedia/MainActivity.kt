package dev.pgm.cocktailpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.pgm.cocktailpedia.ui.screen.navigation.HostNavigation
import dev.pgm.cocktailpedia.ui.theme.CocktailPediaTheme
import dev.pgm.data.CocktailRepository
import dev.pgm.usecases.GetCocktailUseCase
import dev.pgm.usecases.SynchronizeCocktailUseCase


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cocktailRepository =
            CocktailRepository(
                CocktailServerDataSource(),
                CocktailLocalDataSource(this)
            )

        val getCocktailUseCase = GetCocktailUseCase(cocktailRepository)
        val syncCocktailUseCase = SynchronizeCocktailUseCase(cocktailRepository)

        val viewModel = CocktailViewModel(getCocktailUseCase, syncCocktailUseCase)
        enableEdgeToEdge()
        setContent {
            CocktailPediaTheme { HostNavigation(viewModel) }
        }
    }
}
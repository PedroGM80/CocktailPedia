package dev.pgm.cocktailpedia.ui.screen.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.pgm.cocktailpedia.CocktailViewModel
import dev.pgm.cocktailpedia.models.Cocktail
import dev.pgm.cocktailpedia.ui.screen.CocktailDetailScreen
import dev.pgm.cocktailpedia.ui.screen.CocktailListScreen
import kotlinx.serialization.Serializable


@Serializable
object ScreenCocktailList

@Serializable
data class ScreenCocktailDetail(val cocktailId: String)

@Composable
fun HostNavigation(viewModel: CocktailViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val cocktails by viewModel.cocktails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ScreenCocktailList,
            modifier = modifier.padding(paddingValues)
        ) {
            composable<ScreenCocktailList> {
                CocktailListScreen(
                    cocktails = cocktails,
                    isLoading = isLoading,
                    onCocktailClick = { cocktail ->
                        navController.navigate(ScreenCocktailDetail(cocktail.idDrink))
                    },
                    onLetterSelected = { letter ->
                        viewModel.fetchCocktailsByLetter(letter)
                    }
                )
            }

            composable<ScreenCocktailDetail> { backStackEntry ->
                val args = backStackEntry.toRoute<ScreenCocktailDetail>()
                val selectedCocktail = cocktails.find { it.idDrink == args.cocktailId }

                selectedCocktail?.let { cocktail ->
                    CocktailDetailScreen(cocktail = cocktail)
                }
            }
        }
    }
}
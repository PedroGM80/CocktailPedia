package dev.pgm.cocktailpedia.ui.screen.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.pgm.cocktailpedia.CocktailViewModel
import dev.pgm.cocktailpedia.ui.screen.CocktailDetailScreen
import dev.pgm.cocktailpedia.ui.screen.CocktailListScreen
import dev.pgm.cocktailpedia.ui.screen.Screen

private const val COCKTAIL_ID = "cocktailId"

@Composable
fun HostNavigation(viewModel: CocktailViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val cocktails by viewModel.cocktails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.CocktailList.route,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(Screen.CocktailList.route) {
                CocktailListScreen(
                    cocktails = cocktails,
                    isLoading = isLoading,
                    onCocktailClick = { cocktail ->
                        navController.navigate(Screen.CocktailDetail.createRoute(cocktail.idDrink))
                    },
                    onLetterSelected = { letter ->
                        viewModel.fetchCocktailsByLetter(letter)
                    }
                )
            }

            composable(
                route = Screen.CocktailDetail.route,
                arguments = listOf(
                    navArgument(COCKTAIL_ID) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val cocktailId = backStackEntry.arguments?.getString(COCKTAIL_ID)
                val selectedCocktail = cocktails.find { it.idDrink == cocktailId }

                selectedCocktail?.let { cocktail ->
                    CocktailDetailScreen(cocktail = cocktail)
                }
            }
        }
    }
}

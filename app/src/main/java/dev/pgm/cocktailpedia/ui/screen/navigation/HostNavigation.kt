package dev.pgm.cocktailpedia.ui.screen.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.pgm.cocktailpedia.CocktailViewModel
import dev.pgm.cocktailpedia.ui.screen.CocktailDetailScreen
import dev.pgm.cocktailpedia.ui.screen.CocktailListScreen
import dev.pgm.domain.Cocktail


@Composable
fun HostNavigation(viewModel: CocktailViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val cocktails by viewModel.cocktails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    HostNavigation(
        navController = navController,
        cocktails = cocktails,
        isLoading = isLoading,
        fetchCocktailsByLetter = viewModel::fetchCocktailsByLetter,
        navigate = { navController.navigate(it) },
        modifier = modifier
    )
}

@Composable
fun HostNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    cocktails: List<Cocktail>,
    isLoading: Boolean,
    fetchCocktailsByLetter: (String) -> Unit,
    navigate: (ScreenCocktailDetail) -> Unit,
) {
    Scaffold { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = ScreenCocktailList,
            modifier = modifier.padding(paddingValues)
        ) {
            composable<ScreenCocktailList> {
                CocktailListContent(cocktails, isLoading, navigate, fetchCocktailsByLetter)
            }

            composable<ScreenCocktailDetail> { backStackEntry ->
                DetailScreenContent(backStackEntry, cocktails)
            }
        }
    }
}

@Composable
private fun CocktailListContent(
    cocktails: List<Cocktail>,
    isLoading: Boolean,
    navigate: (ScreenCocktailDetail) -> Unit,
    fetchCocktailsByLetter: (String) -> Unit
) {
    CocktailListScreen(
        cocktails = cocktails,
        isLoading = isLoading,
        onCocktailClick = { cocktail ->
            navigate(ScreenCocktailDetail(cocktail.idDrink))
        },
        onLetterSelected = { letter ->
            fetchCocktailsByLetter(letter)
        }
    )
}

@Composable
private fun DetailScreenContent(
    backStackEntry: NavBackStackEntry,
    cocktails: List<Cocktail>
) {
    val args = backStackEntry.toRoute<ScreenCocktailDetail>()
    val selectedCocktail = cocktails.find { it.idDrink == args.cocktailId }
    selectedCocktail?.let { cocktail ->
        CocktailDetailScreen(cocktail = cocktail)
    }
}
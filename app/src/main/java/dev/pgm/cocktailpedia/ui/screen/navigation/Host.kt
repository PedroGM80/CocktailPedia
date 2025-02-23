package dev.pgm.cocktailpedia.ui.screen.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.pgm.cocktailpedia.ui.component.TopBarWithMenu
import dev.pgm.cocktailpedia.ui.screen.AboutScreen
import dev.pgm.cocktailpedia.ui.screen.AddCocktailScreen
import dev.pgm.cocktailpedia.ui.screen.AddCocktailViewModel
import dev.pgm.cocktailpedia.ui.screen.CocktailDetailScreen
import dev.pgm.cocktailpedia.ui.screen.CocktailListScreen
import dev.pgm.cocktailpedia.ui.screen.CocktailViewModel
import dev.pgm.domain.Cocktail


@Composable
fun Host(
    viewModel: CocktailViewModel,
    addCocktailViewModel: AddCocktailViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val cocktails by viewModel.cocktails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    HostNavigation(
        addCocktailViewModel = addCocktailViewModel,
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
    addCocktailViewModel: AddCocktailViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    cocktails: List<Cocktail>,
    isLoading: Boolean,
    fetchCocktailsByLetter: (String) -> Unit,
    navigate: (ScreenCocktailDetail) -> Unit,
) {
    val context = LocalContext.current
    Scaffold(
        topBar = { TopBarWithMenu(navController) }
    ) { paddingValues ->
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

            composable<ScreenAbout> {
                AboutScreen()
            }

            composable<ScreenAddCocktail> {
                AddCocktailScreen(onCocktailAdded = {
                    Toast.makeText(context, "Cocktail added", Toast.LENGTH_SHORT).show()
                }, viewModel = addCocktailViewModel)
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
package dev.pgm.cocktailpedia.ui.screen

sealed class Screen(val route: String) {
    data object CocktailList : Screen("cocktailList")
    data object CocktailDetail : Screen("cocktailDetail/{cocktailId}") {
        fun createRoute(cocktailId: String) = "cocktailDetail/$cocktailId"
    }
}
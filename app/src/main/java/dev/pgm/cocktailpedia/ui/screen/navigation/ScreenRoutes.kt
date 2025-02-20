package dev.pgm.cocktailpedia.ui.screen.navigation

import kotlinx.serialization.Serializable

@Serializable
data class ScreenCocktailDetail(val cocktailId: String)

@Serializable
object ScreenAbout

@Serializable
object ScreenAddCocktail

@Serializable
object ScreenCocktailList

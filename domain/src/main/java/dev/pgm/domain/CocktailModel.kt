package dev.pgm.domain

import androidx.compose.runtime.Stable

@Stable
data class CocktailModel(
    val drinks: List<Cocktail>?
)
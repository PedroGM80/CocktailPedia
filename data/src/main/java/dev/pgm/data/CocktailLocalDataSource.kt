package dev.pgm.data

import dev.pgm.domain.Cocktail
import dev.pgm.domain.CocktailResponse

interface CocktailLocalDataSource {

    suspend fun insertCocktail(cocktail: Cocktail)
    suspend fun insertCocktails(cocktails: List<Cocktail>)

    suspend fun getCocktailById(id: String): Cocktail?
    suspend fun getAllCocktails(): List<Cocktail>
    suspend fun searchCocktails(query: String): List<Cocktail>
    suspend fun getFavoriteCocktails(): List<Cocktail>

    suspend fun updateCocktail(cocktail: Cocktail)
    suspend fun updateFavoriteStatus(cocktailId: String, isFavorite: Boolean)
    suspend fun getCocktailByLetter(firstLetter: String): CocktailResponse
    suspend fun deleteCocktail(cocktailId: String)
    suspend fun deleteAllCocktails()
}
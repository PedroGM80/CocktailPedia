package dev.pgm.domain

interface ICocktailRepository {
    suspend fun getRemoteCocktailsByFirstLetter(firstLetter: String): CocktailModel
    suspend fun getCocktailsByFirstLetter(firstLetter: String): CocktailModel
    suspend fun syncAllCocktails()
    suspend fun getAllLocalCocktails(): List<Cocktail>
}
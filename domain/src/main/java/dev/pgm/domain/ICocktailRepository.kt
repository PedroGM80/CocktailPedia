package dev.pgm.domain

interface ICocktailRepository {
    suspend fun getRemoteCocktailsByFirstLetter(firstLetter: String): CocktailResponse
    suspend fun getCocktailsByFirstLetter(firstLetter: String): CocktailResponse
    suspend fun syncAllCocktails()
    suspend fun getAllLocalCocktails(): List<Cocktail>
}
package dev.pgm.domain

interface ICocktailRepository {
    suspend fun getCocktailsByFirstLetter(firstLetter: String): CocktailResponse
}
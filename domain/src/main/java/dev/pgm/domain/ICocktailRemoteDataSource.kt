package dev.pgm.domain

interface ICocktailRemoteDataSource {
    suspend fun getCocktailsByFirstLetter(firstLetter: String): Result<CocktailModel>
}
package dev.pgm.data

import dev.pgm.domain.CocktailResponse


class CocktailRepository(private val remoteDataSource: CocktailRemoteDataSource) {
    suspend fun getCocktailsByFirstLetter(firstLetter: String): CocktailResponse {
        return remoteDataSource.getCocktailsByFirstLetter(firstLetter)
    }
}
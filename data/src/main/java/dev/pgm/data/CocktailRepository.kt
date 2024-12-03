package dev.pgm.data

import dev.pgm.domain.CocktailResponse
import dev.pgm.domain.ICocktailRepository


class CocktailRepository(private val remoteDataSource: CocktailRemoteDataSource) :
    ICocktailRepository {
    override suspend fun getCocktailsByFirstLetter(firstLetter: String): CocktailResponse {
        return remoteDataSource.getCocktailsByFirstLetter(firstLetter)
    }
}
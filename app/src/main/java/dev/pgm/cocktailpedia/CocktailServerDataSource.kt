package dev.pgm.cocktailpedia

import dev.pgm.data.CocktailRemoteDataSource


class CocktailServerDataSource() : CocktailRemoteDataSource {
    override suspend fun getCocktailsByFirstLetter(firstLetter: String): dev.pgm.domain.CocktailResponse {
        return NetworkModule.api.getCocktailsByFirstLetter(firstLetter)
    }
}
package dev.pgm.data

import dev.pgm.domain.Cocktail
import dev.pgm.domain.CocktailResponse
import dev.pgm.domain.ICocktailRepository

class CocktailRepository(
    private val remoteDataSource: CocktailRemoteDataSource,
    private val localDataSource: CocktailLocalDataSource
) : ICocktailRepository {

    override suspend fun getRemoteCocktailsByFirstLetter(firstLetter: String): CocktailResponse {
        try {
            // Fetch from remote
            val remoteResponse = remoteDataSource.getCocktailsByFirstLetter(firstLetter)

            // Store all cocktails in local database
            remoteResponse.drinks?.forEach { cocktail ->
                localDataSource.insertCocktail(cocktail)
            }

            return remoteResponse
        } catch (e: Exception) {
            // If remote fails, return local data
            val localCocktails = localDataSource.getAllCocktails()
            return CocktailResponse(localCocktails)
        }
    }

    override suspend fun getCocktailsByFirstLetter(firstLetter: String): CocktailResponse {

        return localDataSource.getCocktailByLetter(firstLetter)
    }

    // Single Source of Truth implementation
    override suspend fun getAllLocalCocktails(): List<Cocktail> {
        return localDataSource.getAllCocktails()
    }

    // New function to sync all cocktails from A to Z
    override suspend fun syncAllCocktails() {
        val alphabet = ('A'..'Z').toList()

        try {
            alphabet.forEach { letter ->
                try {
                    val response = remoteDataSource.getCocktailsByFirstLetter(letter.toString())
                    response.drinks?.forEach { cocktail ->
                        localDataSource.insertCocktail(cocktail)
                    }
                } catch (e: Exception) {
                    // Log error but continue with next letter
                    println("Error fetching cocktails for letter $letter: ${e.message}")
                }

            }
        } catch (e: Exception) {
            println("Error during full sync: ${e.message}")
            throw e
        }
    }
}
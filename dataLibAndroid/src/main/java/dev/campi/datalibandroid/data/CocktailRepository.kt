package dev.campi.datalibandroid.data

import android.util.Log
import dev.pgm.domain.Cocktail
import dev.pgm.domain.CocktailModel
import dev.pgm.domain.ICocktailLocalDataSource
import dev.pgm.domain.ICocktailRemoteDataSource
import dev.pgm.domain.ICocktailRepository
import org.koin.core.annotation.Factory

@Factory
class CocktailRepository(
    private val remoteDataSource: ICocktailRemoteDataSource,
    private val localDataSource: ICocktailLocalDataSource
) : ICocktailRepository {

    override suspend fun getRemoteCocktailsByFirstLetter(firstLetter: String): Result<CocktailModel> {
        try {
            // Fetch from remote
            return remoteDataSource.getCocktailsByFirstLetter(firstLetter).onFailure {
                throw it
            }.onSuccess {
                localDataSource.insertCocktails(it.drinks.orEmpty())
            }


        } catch (e: Exception) {
            // If remote fails, return local data
            val localCocktails: List<Cocktail> = localDataSource.getAllCocktails()
            return Result.success(CocktailModel(localCocktails))
        }
    }

    override suspend fun getCocktailsByFirstLetter(firstLetter: String): CocktailModel {

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
                    val result: Result<CocktailModel> =
                        remoteDataSource.getCocktailsByFirstLetter(letter.toString())
                    result.onSuccess {
                        it.drinks?.forEach { cocktail ->
                            localDataSource.insertCocktail(cocktail)
                        }
                    }.onFailure {
                        Log.e(
                            "CocktailRepository",
                            "Error fetching cocktails for letter $letter: ${it.message}"
                        )
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
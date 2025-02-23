package dev.pgm.usecases

import dev.pgm.domain.ICocktailRepository
import org.koin.core.annotation.Factory


@Factory
class GetCocktailUseCase(private val repository: ICocktailRepository) {
    suspend fun getCocktailsByFirstLetter(firstLetter: String) =
        repository.getCocktailsByFirstLetter(firstLetter)
}
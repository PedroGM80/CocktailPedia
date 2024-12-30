package dev.pgm.usecases

import dev.pgm.domain.ICocktailRepository


class GetCocktailUseCase(private val repository: ICocktailRepository) {
    suspend fun getCocktailsByFirstLetter(firstLetter: String) =
        repository.getCocktailsByFirstLetter(firstLetter)
}
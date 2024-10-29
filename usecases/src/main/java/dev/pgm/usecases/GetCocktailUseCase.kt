package dev.pgm.usecases

import dev.pgm.data.CocktailRepository


class GetCocktailUseCase(private val repository: CocktailRepository) {
    suspend fun getCocktailsByFirstLetter(firstLetter: String) =
        repository.getCocktailsByFirstLetter(firstLetter)
}
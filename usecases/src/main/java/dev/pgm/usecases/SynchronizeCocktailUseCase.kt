package dev.pgm.usecases

import dev.pgm.domain.ICocktailRepository

class SynchronizeCocktailUseCase(private val repository: ICocktailRepository) {
    suspend fun synchronizeCocktails() {
        repository.syncAllCocktails()
    }
}
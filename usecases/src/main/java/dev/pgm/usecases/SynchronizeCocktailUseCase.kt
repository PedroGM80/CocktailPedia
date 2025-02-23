package dev.pgm.usecases

import dev.pgm.domain.ICocktailRepository
import org.koin.core.annotation.Factory


@Factory
class SynchronizeCocktailUseCase(private val repository: ICocktailRepository) {
    suspend fun synchronizeCocktails() {
        repository.syncAllCocktails()
    }
}
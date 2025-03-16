package dev.pgm.usecases

import dev.pgm.domain.ICocktailRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SynchronizeCocktailUseCaseTest {

    private val repository = mock<ICocktailRepository>()

    private val useCase = SynchronizeCocktailUseCase(repository)

    @Test
    fun `should call repository to synchronize cocktails`() = runBlocking {
        useCase.synchronizeCocktails()

        verify(repository, times(1)).syncAllCocktails()
    }
}

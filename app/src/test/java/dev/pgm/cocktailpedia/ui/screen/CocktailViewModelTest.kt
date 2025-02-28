package dev.pgm.cocktailpedia.ui.screen

import dev.pgm.data.CocktailRepository
import dev.pgm.domain.Cocktail
import dev.pgm.domain.CocktailResponse
import dev.pgm.testrules.CoroutinesTestRules
import dev.pgm.tunit.getSampleCocktails
import dev.pgm.usecases.GetCocktailUseCase
import dev.pgm.usecases.SynchronizeCocktailUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CocktailViewModelTest {

    val sampleCocktails = getSampleCocktails()
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRules()

    @get:Rule
    val mockkRule = MockKRule(this)
    lateinit var viewModel: CocktailViewModel
    lateinit var getCocktailUseCase: GetCocktailUseCase
    lateinit var syncCocktailUseCase: SynchronizeCocktailUseCase

    @Before
    fun setup() {
        val repository = CocktailRepository(mockk(), mockk())
        syncCocktailUseCase = SynchronizeCocktailUseCase(repository)
        getCocktailUseCase = GetCocktailUseCase( repository)

        viewModel = CocktailViewModel(
            getCocktailUseCase = getCocktailUseCase,
            syncCocktailUseCase = syncCocktailUseCase
        )
    }


    @Test
    fun `init should synchronize cocktails`() = runTest {
        coEvery { syncCocktailUseCase.synchronizeCocktails() } just mockk()
        viewModel.cocktails
        coVerify { syncCocktailUseCase.synchronizeCocktails() }
    }

    @Test
    fun `get cocktails should initially return empty list`() = runTest {
        assertEquals(emptyList<Cocktail>(), viewModel.cocktails.first())
    }

    @Test
    fun `is loading should initially be false`() = runTest {
        assertFalse(viewModel.isLoading.first())
    }

    @Test
    fun `fetch cocktails by letter should update cocktails state`() = runTest {
        val letter = "M"
        val cocktailResponse = CocktailResponse(drinks = sampleCocktails)
        coEvery { getCocktailUseCase.getCocktailsByFirstLetter(letter) } returns cocktailResponse

        viewModel.fetchCocktailsByLetter(letter)
        advanceUntilIdle()

        assertEquals(sampleCocktails, viewModel.cocktails.first())
        coVerify { getCocktailUseCase.getCocktailsByFirstLetter(letter) }
    }

    @Test
    fun `fetch cocktails by letter should handle null response`() = runTest {
        val letter = "Z"
        val cocktailResponse = CocktailResponse(drinks = null)
        coEvery { getCocktailUseCase.getCocktailsByFirstLetter(letter) } returns cocktailResponse

        viewModel.fetchCocktailsByLetter(letter)
        advanceUntilIdle()

        assertEquals(emptyList<Cocktail>(), viewModel.cocktails.first())
    }

    @Test
    fun `fetch cocktails by letter should return empty list when no cocktails found`() = runTest {
        val letter = "Q"
        val cocktailResponse = CocktailResponse(drinks = emptyList())
        coEvery { getCocktailUseCase.getCocktailsByFirstLetter(letter) } returns cocktailResponse

        viewModel.fetchCocktailsByLetter(letter)
        advanceUntilIdle()

        assertEquals(emptyList<Cocktail>(), viewModel.cocktails.first())
    }
}
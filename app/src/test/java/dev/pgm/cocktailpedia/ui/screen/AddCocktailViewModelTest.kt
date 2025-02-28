package dev.pgm.cocktailpedia.ui.screen

import android.content.Context
import dev.pgm.cocktailpedia.CocktailLocalDataSource
import dev.pgm.testrules.CoroutinesTestRules
import dev.pgm.tunit.getSampleCocktails
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddCocktailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRules()

    private lateinit var viewModel: AddCocktailViewModel
    private lateinit var mockContext: Context
    private lateinit var mockDataSource: CocktailLocalDataSource

    @Before
    fun setup() {
        mockContext = mockk<Context>(relaxed = true)

        mockDataSource = mockk(relaxed = true)

        viewModel = AddCocktailViewModel()


        every { mockContext.applicationContext } returns mockContext
        every { CocktailLocalDataSource(mockContext) } returns mockDataSource
    }

    @Test
    fun `addCocktail should successfully add a cocktail and set loading state correctly`() = runTest {
        val mockCocktail = getSampleCocktails().first()

        viewModel.addCocktail(mockCocktail, mockContext)

        advanceUntilIdle()

        verify {
            runBlocking {
                mockDataSource.insertCocktail(mockCocktail)

            }
        }

        assertEquals(true, viewModel._isLoading.value)
        assertEquals(null, viewModel._error.value)

        assertEquals(false, viewModel._isLoading.value)
    }

    @Test
    fun `addCocktail should handle errors and set error state correctly`() = runTest {
        val mockCocktail = getSampleCocktails().first()

        coEvery { mockDataSource.insertCocktail(any()) } throws Exception("Database error")

        viewModel.addCocktail(mockCocktail, mockContext)

        advanceUntilIdle()

        assertEquals("Error saving cocktail: Database error", viewModel._error.value)

        assertEquals(false, viewModel._isLoading.value)
    }
}

package dev.pgm.cocktailpedia.ui.screen

import android.content.Context
import dev.campi.datalibandroid.CocktailLocalDataSourceImpl
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
    private lateinit var mockDataSource: CocktailLocalDataSourceImpl

    @Before
    fun setup() {
        mockContext = mockk<Context>(relaxed = true)

        mockDataSource = mockk(relaxed = true)

        viewModel = AddCocktailViewModel(
            dataSource = mockDataSource,
            createImageUriUseCase = mockk(),
        )


        every { mockContext.applicationContext } returns mockContext
        every { CocktailLocalDataSourceImpl(mockContext) } returns mockDataSource
    }

    @Test
    fun `addCocktail should successfully add a cocktail and set loading state correctly`() =
        runTest {
            val mockCocktail = getSampleCocktails().first()

            viewModel.addCocktail(mockCocktail, mockContext)

            advanceUntilIdle()

            verify {
                runBlocking {
                    mockDataSource.insertCocktail(mockCocktail)

                }
            }

            assertEquals(true, viewModel.isLoading.value)
            assertEquals(null, viewModel.error.value)

            assertEquals(false, viewModel.isLoading.value)
        }

    @Test
    fun `addCocktail should handle errors and set error state correctly`() = runTest {
        val mockCocktail = getSampleCocktails().first()

        coEvery { mockDataSource.insertCocktail(any()) } throws Exception("Database error")

        viewModel.addCocktail(mockCocktail, mockContext)

        advanceUntilIdle()

        assertEquals("Error saving cocktail: Database error", viewModel.error.value)

        assertEquals(false, viewModel.isLoading.value)
    }
}

package dev.pgm.cocktailpedia.ui.screen

import android.content.Context
import dev.campi.datalibandroid.CocktailLocalDataSourceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private lateinit var mockCreateImageUriUseCase: CreateImageUriUseCase

    @Before
    fun setup() {
        mockContext = mockk(relaxed = true)
        mockDataSource = mockk(relaxed = true)
        mockCreateImageUriUseCase = mockk(relaxed = true)

        viewModel = AddCocktailViewModel(
            dataSource = mockDataSource,
            createImageUriUseCase = mockCreateImageUriUseCase
        )
    }

    @Test
    fun `addCocktail should successfully add a cocktail and set loading state correctly`() =
        runTest {
            val mockCocktail = getSampleCocktails().first()

            coEvery { mockDataSource.insertCocktail(any()) } returns Unit

            viewModel.addCocktail(mockCocktail, mockContext)

            advanceUntilIdle()

            coVerify { mockDataSource.insertCocktail(mockCocktail) }

            assertEquals(false, viewModel.isLoading.value)
            assertEquals(null, viewModel.error.value)
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
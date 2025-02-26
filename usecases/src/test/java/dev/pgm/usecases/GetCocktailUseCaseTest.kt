package dev.pgm.usecases

import dev.pgm.domain.Cocktail
import dev.pgm.domain.CocktailResponse
import dev.pgm.domain.ICocktailRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetCocktailUseCaseTest {

    private val repository = mock<ICocktailRepository>()

    private val useCase = GetCocktailUseCase(repository)

    @Test
    fun `get cocktails`() = runBlocking {

        val firstLetter = "A"

        val mockResponse = CocktailResponse(
            drinks = listOf(
                Cocktail(
                    isFavorite = false,
                    idDrink = "1",
                    strDrink = "Margarita",
                    strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
                    strInstructions = "Mix and serve in a glass.",
                    strCategory = "Cocktail",
                    strAlcoholic = "Alcoholic",
                    strGlass = "Cocktail glass",
                    strIngredient1 = "Tequila",
                    strIngredient2 = "Lime juice",
                    strIngredient3 = "Triple sec",
                    strIngredient4 = null,
                    strIngredient5 = null,
                    strMeasure1 = "50 ml",
                    strMeasure2 = "30 ml",
                    strMeasure3 = "20 ml",
                    strMeasure4 = null,
                    strMeasure5 = null
                ),
                Cocktail(
                    isFavorite = false,
                    idDrink = "2",
                    strDrink = "Amaretto",
                    strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/92h3jz1582474310.jpg",
                    strInstructions = "Shake and strain into a glass.",
                    strCategory = "Cocktail",
                    strAlcoholic = "Alcoholic",
                    strGlass = "Martini glass",
                    strIngredient1 = "Amaretto",
                    strIngredient2 = "Vodka",
                    strIngredient3 = null,
                    strIngredient4 = null,
                    strIngredient5 = null,
                    strMeasure1 = "40 ml",
                    strMeasure2 = "20 ml",
                    strMeasure3 = null,
                    strMeasure4 = null,
                    strMeasure5 = null
                )
            )
        )

        whenever(repository.getCocktailsByFirstLetter(firstLetter)).thenReturn(mockResponse)

        val result = useCase.getCocktailsByFirstLetter(firstLetter)

        verify(repository, times(1)).getCocktailsByFirstLetter(firstLetter)

        assert(result == mockResponse)
    }
}
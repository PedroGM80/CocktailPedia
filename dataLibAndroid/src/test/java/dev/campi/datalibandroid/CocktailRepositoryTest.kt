package dev.campi.datalibandroid


import dev.campi.datalibandroid.data.CocktailRepository
import dev.pgm.domain.CocktailModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CocktailRepositoryTest {

    @Mock
    private lateinit var remoteDataSource: CocktailServerDataSourceImpl

    @Mock
    private lateinit var localDataSource: CocktailLocalDataSourceImpl

    private lateinit var repository: CocktailRepository

    private val mockCocktails = getSampleCocktails()

    private val mockResponse: Result<CocktailModel> = Result.success(CocktailModel(mockCocktails))


    @Before
    fun setup() {
        repository = CocktailRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `get remote cocktails by first letter fetches from remote and stores locally`() {
        runBlocking {
            `when`(remoteDataSource.getCocktailsByFirstLetter("M")).thenReturn(mockResponse)

            val result = repository.getRemoteCocktailsByFirstLetter("M")

            assertEquals(mockResponse, result)
            verify(remoteDataSource).getCocktailsByFirstLetter("M")
            for (cocktail in mockCocktails) {
                verify(localDataSource).insertCocktail(cocktail)
            }
        }
    }

    @Test
    fun `get remote cocktails by first letter falls back to local when remote fails`() {
        runBlocking {

            `when`(remoteDataSource.getCocktailsByFirstLetter("M")).thenThrow(RuntimeException("Network error"))
            `when`(localDataSource.getAllCocktails()).thenReturn(mockCocktails)

            val result = repository.getRemoteCocktailsByFirstLetter("M")

            assertEquals(mockCocktails, result)
            verify(localDataSource).getAllCocktails()
        }
    }

    @Test
    fun ` get cocktails by first letter returns data from local source`() {
        runBlocking {
            `when`(localDataSource.getCocktailByLetter("M")).thenReturn(mockResponse.getOrNull())

            val result = repository.getCocktailsByFirstLetter("M")

            assertEquals(mockResponse, result)
            verify(localDataSource).getCocktailByLetter("M")
        }
    }

    @Test
    fun `get all local cocktails returns data from local source`() {
        runBlocking {
            `when`(localDataSource.getAllCocktails()).thenReturn(mockCocktails)

            val result = repository.getAllLocalCocktails()

            assertEquals(mockCocktails, result)
            verify(localDataSource).getAllCocktails()
        }
    }

    @Test
    fun `sync all cocktails fetches from remote and stores locally`() {
        runBlocking {
            for (letter in 'A'..'Z') {
                `when`(remoteDataSource.getCocktailsByFirstLetter(letter.toString())).thenReturn(
                    mockResponse
                )
            }

            repository.syncAllCocktails()

            for (letter in 'A'..'Z') {
                verify(remoteDataSource).getCocktailsByFirstLetter(letter.toString())
                for (cocktail in mockCocktails) {
                    verify(localDataSource, atLeastOnce()).insertCocktail(cocktail)
                }
            }
        }
    }

    @Test
    fun `sync all cocktails continues even if some letters fail`() {
        runBlocking {
            for (letter in 'A'..'Z') {
                if (letter == 'M') {
                    `when`(remoteDataSource.getCocktailsByFirstLetter(letter.toString()))
                        .thenThrow(RuntimeException("Network error for M"))
                } else {
                    `when`(remoteDataSource.getCocktailsByFirstLetter(letter.toString()))
                        .thenReturn(mockResponse)
                }
            }

            repository.syncAllCocktails()

            verify(remoteDataSource).getCocktailsByFirstLetter("A")
            verify(remoteDataSource).getCocktailsByFirstLetter("Z")

            verify(remoteDataSource).getCocktailsByFirstLetter("M")
        }
    }

    @Test(expected = RuntimeException::class)
    fun `sync all cocktails throws exception on critical failure`() {
        runBlocking {
            repository.syncAllCocktails()

            verify(repository, times(1)).syncAllCocktails()
        }
    }
}
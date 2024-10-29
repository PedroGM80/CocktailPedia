package dev.pgm.data


import dev.pgm.domain.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailRemoteDataSource {
    @GET("search.php")
    suspend fun getCocktailsByFirstLetter(@Query("f") firstLetter: String): CocktailResponse
}
package dev.pgm.cocktailpedia

import dev.pgm.cocktailpedia.models.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    @GET("search.php")
    suspend fun getCocktailsByFirstLetter(@Query("f") firstLetter: String): CocktailResponse
}
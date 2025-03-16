package dev.campi.datalibandroid


import dev.campi.datalibandroid.data.CocktailListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ICocktailRemoteRetrofit {
    @GET("search.php")
    suspend fun getCocktailsByFirstLetter(@Query("f") firstLetter: String): Response<CocktailListResponse>
}
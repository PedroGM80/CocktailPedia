package dev.campi.datalibandroid

import dev.campi.datalibandroid.dto.CocktailListResponse
import dev.campi.datalibandroid.dto.toDomain
import dev.pgm.domain.CocktailModel
import dev.pgm.domain.ICocktailRemoteDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Factory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

@Factory
class CocktailServerDataSource() : ICocktailRemoteDataSource {
    override suspend fun getCocktailsByFirstLetter(firstLetter: String): Result<CocktailModel>{
        val response = NetworkModule.api.getCocktailsByFirstLetter(firstLetter)
        if (!response.isSuccessful) {
            return Result.failure( Exception("Error fetching cocktails: ${response.message()}"))
        }
        return Result.success(response.body()?.toDomain() ?: CocktailModel(emptyList()))
    }
}

object NetworkModule {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    val api: ICocktailRemoteRetrofit by lazy {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Puedes usar HEADERS, BASIC, o NONE
            }).build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ICocktailRemoteRetrofit::class.java)
    }
}

interface ICocktailRemoteRetrofit {
    @GET("search.php")
    suspend fun getCocktailsByFirstLetter(@Query("f") firstLetter: String): Response<CocktailListResponse>
}
package dev.campi.datalibandroid

import dev.pgm.domain.CocktailModel
import dev.pgm.domain.ICocktailRemoteDataSource
import org.koin.core.annotation.Factory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Factory
class CocktailServerDataSource() : ICocktailRemoteDataSource {
    override suspend fun getCocktailsByFirstLetter(firstLetter: String): CocktailModel {
        return NetworkModule.api.getCocktailsByFirstLetter(firstLetter)
    }
}

object NetworkModule {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    val api: ICocktailRemoteDataSource by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ICocktailRemoteDataSource::class.java)
    }
}
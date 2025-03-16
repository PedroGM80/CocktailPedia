package dev.campi.datalibandroid.framework


import dev.campi.datalibandroid.ICocktailRemoteRetrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

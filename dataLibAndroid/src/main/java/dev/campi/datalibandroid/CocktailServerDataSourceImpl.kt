package dev.campi.datalibandroid

import dev.campi.datalibandroid.dto.toDomain
import dev.campi.datalibandroid.framework.NetworkModule
import dev.pgm.domain.CocktailModel
import dev.pgm.domain.ICocktailRemoteDataSource
import org.koin.core.annotation.Factory

@Factory
class CocktailServerDataSourceImpl() : ICocktailRemoteDataSource {
    override suspend fun getCocktailsByFirstLetter(firstLetter: String): Result<CocktailModel>{
        val response = NetworkModule.api.getCocktailsByFirstLetter(firstLetter)
        if (!response.isSuccessful) {
            return Result.failure( Exception("Error fetching cocktails: ${response.message()}"))
        }
        return Result.success(response.body()?.toDomain() ?: CocktailModel(emptyList()))
    }
}


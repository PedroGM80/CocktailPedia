package dev.pgm.cocktailpedia

import android.content.Context
import dev.pgm.cocktailpedia.framework.dataBase.CocktailDatabase
import dev.pgm.cocktailpedia.framework.dataBase.toCocktail
import dev.pgm.cocktailpedia.framework.dataBase.toCocktailDto
import dev.pgm.data.CocktailLocalDataSource
import dev.pgm.domain.Cocktail
import dev.pgm.domain.CocktailResponse

class CocktailLocalDataSource(context: Context) : CocktailLocalDataSource {
    private val db = CocktailDatabase.getDatabase(context)
    private val dao = db.cocktailDao()

    override suspend fun insertCocktail(cocktail: Cocktail) {
        dao.insertCocktail(cocktail.toCocktailDto())
    }

    override suspend fun insertCocktails(cocktails: List<Cocktail>) {
        dao.insertCocktails(cocktails.map { it.toCocktailDto() })
    }

    override suspend fun getCocktailById(id: String): Cocktail? {
        return dao.getCocktailById(id)?.toCocktail()
    }

    override suspend fun getAllCocktails(): List<Cocktail> {
        return dao.getAllCocktails().map { it.toCocktail() }
    }

    override suspend fun searchCocktails(query: String): List<Cocktail> {
        return dao.searchCocktails(query).map { it.toCocktail() }
    }

    override suspend fun getFavoriteCocktails(): List<Cocktail> {
        return dao.getFavoriteCocktails().map { it.toCocktail() }
    }

    override suspend fun updateCocktail(cocktail: Cocktail) {
        dao.updateCocktail(cocktail.toCocktailDto())
    }

    override suspend fun updateFavoriteStatus(cocktailId: String, isFavorite: Boolean) {
        dao.updateFavoriteStatus(cocktailId, isFavorite)
    }

    override suspend fun getCocktailByLetter(firstLetter: String): CocktailResponse {
        val cocktails = dao.getCocktailsByLetter(firstLetter).map { it.toCocktail() }
        return CocktailResponse(cocktails)
    }

    override suspend fun deleteCocktail(cocktailId: String) {
        dao.deleteCocktail(cocktailId)
    }

    override suspend fun deleteAllCocktails() {
        dao.deleteAllCocktails()
    }
}
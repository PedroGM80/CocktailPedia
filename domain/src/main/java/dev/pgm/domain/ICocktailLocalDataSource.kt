package dev.pgm.domain

interface ICocktailLocalDataSource {

    suspend fun insertCocktail(cocktail: Cocktail)
    suspend fun insertCocktails(cocktails: List<Cocktail>)

    suspend fun getCocktailById(id: String): Cocktail?
    suspend fun getAllCocktails(): List<Cocktail>
    suspend fun searchCocktails(query: String): List<Cocktail>
    suspend fun getFavoriteCocktails(): List<Cocktail>

    suspend fun updateCocktail(cocktail: Cocktail)
    suspend fun updateFavoriteStatus(cocktailId: String, isFavorite: Boolean)
    suspend fun getCocktailByLetter(firstLetter: String): CocktailModel
    suspend fun deleteCocktail(cocktailId: String)
    suspend fun deleteAllCocktails()
}
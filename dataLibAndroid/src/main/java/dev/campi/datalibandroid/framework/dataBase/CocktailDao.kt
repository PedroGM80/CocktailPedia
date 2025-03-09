package dev.campi.datalibandroid.framework.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(cocktail: CocktailDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktails(cocktails: List<CocktailDto>)

    @Query("SELECT * FROM cocktails WHERE idDrink = :id")
    suspend fun getCocktailById(id: String): CocktailDto?

    @Query("SELECT * FROM cocktails")
    suspend fun getAllCocktails(): List<CocktailDto>

    @Query("SELECT * FROM cocktails WHERE strDrink LIKE '%' || :query || '%'")
    suspend fun searchCocktails(query: String): List<CocktailDto>

    @Query("SELECT * FROM cocktails WHERE isFavorite = 1")
    suspend fun getFavoriteCocktails(): List<CocktailDto>

    @Update
    suspend fun updateCocktail(cocktail: CocktailDto)

    @Query("UPDATE cocktails SET isFavorite = :isFavorite WHERE idDrink = :cocktailId")
    suspend fun updateFavoriteStatus(cocktailId: String, isFavorite: Boolean)

    @Query("SELECT * FROM cocktails WHERE strDrink LIKE :firstLetter || '%'")
    suspend fun getCocktailsByLetter(firstLetter: String): List<CocktailDto>

    @Query("DELETE FROM cocktails WHERE idDrink = :cocktailId")
    suspend fun deleteCocktail(cocktailId: String)

    @Query("DELETE FROM cocktails")
    suspend fun deleteAllCocktails()
}

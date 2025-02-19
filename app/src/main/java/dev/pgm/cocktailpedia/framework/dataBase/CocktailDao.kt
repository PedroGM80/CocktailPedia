package dev.pgm.cocktailpedia.framework.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Query("SELECT * FROM cocktails")
    fun getAllCocktails(): Flow<List<CocktailDto>>

    @Query("SELECT * FROM cocktails WHERE idDrink = :id")
    suspend fun getCocktailById(id: String): CocktailDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(cocktail: CocktailDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktails(cocktails: List<CocktailDto>)

    @Query("DELETE FROM cocktails WHERE idDrink = :id")
    suspend fun deleteCocktailById(id: String)

    @Delete
    suspend fun deleteCocktail(cocktail: CocktailDto)

    @Query("DELETE FROM cocktails")
    suspend fun deleteAllCocktails()
}

package dev.pgm.cocktailpedia.framework.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

const val COCKTAIL_DATABASE = "cocktail_database"

@Database(entities = [CocktailDto::class], version = 1, exportSchema = false)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
    
    companion object {
        @Volatile
        private var INSTANCE: CocktailDatabase? = null
        
        fun getDatabase(context: Context): CocktailDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CocktailDatabase::class.java,
                    COCKTAIL_DATABASE
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package dev.campi.datalibandroid.framework.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.pgm.domain.Cocktail

@Entity(tableName = "cocktails")
data class CocktailDto(
    @PrimaryKey
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String,
    val strInstructions: String,
    val strCategory: String?,
    val strAlcoholic: String?,
    val strGlass: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val isFavorite: Boolean
)

// Extension functions for mapping between domain and data layers
 fun Cocktail.toCocktailDto(): CocktailDto {
    return CocktailDto(
        isFavorite = isFavorite,
        idDrink = idDrink,
        strDrink = strDrink,
        strDrinkThumb = strDrinkThumb,
        strInstructions = strInstructions,
        strCategory = strCategory,
        strAlcoholic = strAlcoholic,
        strGlass = strGlass,
        strIngredient1 = strIngredient1,
        strIngredient2 = strIngredient2,
        strIngredient3 = strIngredient3,
        strIngredient4 = strIngredient4,
        strIngredient5 = strIngredient5,
        strMeasure1 = strMeasure1,
        strMeasure2 = strMeasure2,
        strMeasure3 = strMeasure3,
        strMeasure4 = strMeasure4,
        strMeasure5 = strMeasure5
    )
}

fun CocktailDto.toCocktail(): Cocktail {
    return Cocktail(
        isFavorite = false,
        idDrink = idDrink,
        strDrink = strDrink,
        strDrinkThumb = strDrinkThumb,
        strInstructions = strInstructions,
        strCategory = strCategory,
        strAlcoholic = strAlcoholic,
        strGlass = strGlass,
        strIngredient1 = strIngredient1,
        strIngredient2 = strIngredient2,
        strIngredient3 = strIngredient3,
        strIngredient4 = strIngredient4,
        strIngredient5 = strIngredient5,
        strMeasure1 = strMeasure1,
        strMeasure2 = strMeasure2,
        strMeasure3 = strMeasure3,
        strMeasure4 = strMeasure4,
        strMeasure5 = strMeasure5
    )
}
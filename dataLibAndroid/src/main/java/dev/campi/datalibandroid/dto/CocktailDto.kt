package dev.campi.datalibandroid.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

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
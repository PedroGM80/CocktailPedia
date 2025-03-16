package dev.campi.datalibandroid.data


import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class CocktailResponse(
    val isFavorite: Boolean,
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
    val strMeasure5: String?
)
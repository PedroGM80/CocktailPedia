package dev.campi.datalibandroid.dto


import androidx.annotation.Keep
import dev.pgm.domain.Cocktail
import dev.pgm.domain.CocktailModel
import kotlinx.serialization.SerialName
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

@Keep
@Serializable
data class CocktailListResponse(
    @SerialName("drinks")
    val drinks: List<CocktailResponse>
)

fun CocktailListResponse.toDomain(): CocktailModel {
    return CocktailModel(
        drinks = drinks.map { it.toDomain() }
    )
}

fun CocktailResponse.toDomain(): Cocktail {
    return Cocktail(
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

fun CocktailModel.toDto(): CocktailListResponse {
    return this@toDto.drinks?.let {
        CocktailListResponse(
            drinks = it.map { cocktail -> cocktail.toDto() }
        )
    } ?: CocktailListResponse(emptyList())
}

fun Cocktail.toDto(): CocktailResponse {
    return CocktailResponse(
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
        strMeasure5 = strMeasure5,
    )
}



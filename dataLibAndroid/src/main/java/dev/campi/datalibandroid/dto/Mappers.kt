package dev.campi.datalibandroid.dto


import dev.campi.datalibandroid.data.CocktailListResponse
import dev.campi.datalibandroid.data.CocktailResponse
import dev.pgm.domain.Cocktail
import dev.pgm.domain.CocktailModel


fun CocktailListResponse.toDomain(): CocktailModel {
    return CocktailModel(
        drinks = drinks.map { it.toDomain() })
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

fun CocktailModel.toCocktailResponse(): CocktailListResponse {
    return this@toCocktailResponse.drinks?.let {
        CocktailListResponse(
            drinks = it.map { cocktail -> cocktail.toCocktailResponse() })
    } ?: CocktailListResponse(emptyList())
}

fun Cocktail.toCocktailResponse(): CocktailResponse {
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
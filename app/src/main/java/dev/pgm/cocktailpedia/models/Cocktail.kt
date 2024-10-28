package dev.pgm.cocktailpedia.models


data class Cocktail(
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
) {
    fun getIngredientsList(): List<String> {
        return listOfNotNull(
            strIngredient1, strIngredient2, strIngredient3,
            strIngredient4, strIngredient5
        )
    }

    fun getMeasuresList(): List<String> {
        return listOfNotNull(
            strMeasure1, strMeasure2, strMeasure3,
            strMeasure4, strMeasure5
        )
    }
}
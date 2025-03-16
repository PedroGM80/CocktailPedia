package dev.campi.datalibandroid.data


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Keep
@Serializable
data class CocktailListResponse(
    @SerialName("drinks")
    val drinks: List<CocktailResponse>
)
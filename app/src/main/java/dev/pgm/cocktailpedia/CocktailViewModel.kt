package dev.pgm.cocktailpedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dev.pgm.domain.Cocktail
import dev.pgm.usecases.GetCocktailUseCase

class CocktailViewModel(private val getCocktailUseCase: GetCocktailUseCase) : ViewModel() {
    private val _cocktails = MutableStateFlow<List<Cocktail>>(emptyList())
    val cocktails: StateFlow<List<Cocktail>> = _cocktails

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchCocktailsByLetter(letter: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val response = getCocktailUseCase.getCocktailsByFirstLetter(letter)
            _cocktails.value = response.drinks ?: emptyList()
            _isLoading.value = false
        }
    }
}

package dev.pgm.cocktailpedia.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pgm.domain.Cocktail
import dev.pgm.usecases.GetCocktailUseCase
import dev.pgm.usecases.SynchronizeCocktailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class CocktailViewModel(private val getCocktailUseCase: GetCocktailUseCase
,  private val syncCocktailUseCase: SynchronizeCocktailUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            syncCocktailUseCase.synchronizeCocktails()
        }
    }

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

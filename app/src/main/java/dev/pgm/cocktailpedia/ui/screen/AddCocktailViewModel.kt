package dev.pgm.cocktailpedia.ui.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pgm.domain.Cocktail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel


@KoinViewModel
class AddCocktailViewModel(
    private val dataSource: dev.pgm.domain.ICocktailLocalDataSource,
    private val createImageUriUseCase: CreateImageUriUseCase,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error = _error

    val uri by lazy {
        createImageUriUseCase()
    }

    fun addCocktail(cocktail: Cocktail,context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                dataSource.insertCocktail(cocktail)
            } catch (e: Exception) {
                _error.value = "Error saving cocktail: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
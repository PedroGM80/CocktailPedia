package dev.pgm.cocktailpedia.ui.screen

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.pgm.cocktailpedia.CocktailLocalDataSource
import dev.pgm.domain.Cocktail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@KoinViewModel
class AddCocktailViewModel : ViewModel() {
    internal var temporaryFileUri: Uri? = null

    internal val _isLoading = MutableStateFlow(false)
    internal    val _error = MutableStateFlow<String?>(null)


    fun createImageUri(context: Context): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val tmpFile = File.createTempFile(
            "COCKTAIL_${timeStamp}_",
            ".jpg",
            context.cacheDir
        ).apply {
            createNewFile()
            deleteOnExit()
        }

        temporaryFileUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            tmpFile
        )

        return temporaryFileUri!!
    }

    fun getTemporaryFileUri(): Uri? = temporaryFileUri

    fun addCocktail(cocktail: Cocktail,context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val dataSource = CocktailLocalDataSource(context)
                dataSource.insertCocktail(cocktail)
            } catch (e: Exception) {
                _error.value = "Error saving cocktail: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
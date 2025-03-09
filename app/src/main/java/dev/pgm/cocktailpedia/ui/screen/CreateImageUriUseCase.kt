package dev.pgm.cocktailpedia.ui.screen

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import org.koin.core.annotation.Factory
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Factory
class CreateImageUriUseCase(
    private val context: Context
) {

    operator fun invoke(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val tmpFile = File.createTempFile(
            "COCKTAIL_${timeStamp}_",
            ".jpg",
            context.cacheDir
        ).apply {
            createNewFile()
            deleteOnExit()
        }

        val temporaryFileUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            tmpFile
        )

        return temporaryFileUri
    }
}
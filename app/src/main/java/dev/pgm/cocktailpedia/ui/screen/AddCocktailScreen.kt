package dev.pgm.cocktailpedia.ui.screen

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.pgm.domain.Cocktail
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCocktailScreen(
    onCocktailAdded: () -> Unit, viewModel: AddCocktailViewModel
) {
    var name by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var isAlcoholic by remember { mutableStateOf(true) }
    var glass by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    var ingredients by remember { mutableStateOf(List(5) { "" }) }
    var measures by remember { mutableStateOf(List(5) { "" }) }
    var isFavorite by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoUri = viewModel.getTemporaryFileUri()
        }
    }


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            cameraLauncher.launch(viewModel.createImageUri(context))
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Add New Cocktail") }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ), navigationIcon = {
            IconButton(onClick = { onCocktailAdded() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                val newCocktail = Cocktail(
                    idDrink = UUID.randomUUID().toString(),
                    strDrink = name,
                    strDrinkThumb = photoUri?.toString() ?: "",
                    strInstructions = instructions,
                    strCategory = category,
                    strAlcoholic = if (isAlcoholic) "Alcoholic" else "Non alcoholic",
                    strGlass = glass,
                    strIngredient1 = ingredients[0].takeIf { it.isNotBlank() },
                    strIngredient2 = ingredients[1].takeIf { it.isNotBlank() },
                    strIngredient3 = ingredients[2].takeIf { it.isNotBlank() },
                    strIngredient4 = ingredients[3].takeIf { it.isNotBlank() },
                    strIngredient5 = ingredients[4].takeIf { it.isNotBlank() },
                    strMeasure1 = measures[0].takeIf { it.isNotBlank() },
                    strMeasure2 = measures[1].takeIf { it.isNotBlank() },
                    strMeasure3 = measures[2].takeIf { it.isNotBlank() },
                    strMeasure4 = measures[3].takeIf { it.isNotBlank() },
                    strMeasure5 = measures[4].takeIf { it.isNotBlank() },
                    isFavorite = isFavorite
                )

                viewModel.addCocktail(cocktail = newCocktail, context = context)
                onCocktailAdded()
            },

            ) {
            Icon(Icons.Filled.Save, "Save cocktail")
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {

                Column {
                    if (photoUri != null) {
                        Card(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            AsyncImage(
                                model = photoUri,
                                contentDescription = "Cocktail photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = FillBounds
                            )
                        }

                        FloatingActionButton(
                            onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(Icons.Default.CameraAlt, "Retake photo")
                        }
                        Spacer(modifier = Modifier.padding(bottom = 16.dp))
                        GalleryPickerButton { uri ->
                            photoUri = uri
                        }
                    } else {
                        OutlinedButton(onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) }) {
                            Icon(Icons.Filled.AddAPhoto, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Take Photo")
                        }
                        Spacer(modifier = Modifier.padding(bottom = 16.dp))
                        GalleryPickerButton { uri ->
                            photoUri = uri
                        }
                    }
                }
            }

            OutlinedTextField(value = name,
                onValueChange = { name = it },
                label = { Text("Cocktail Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(value = instructions,
                onValueChange = { instructions = it },
                label = { Text("Instructions") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            OutlinedTextField(value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Alcoholic")
                Spacer(modifier = Modifier.width(16.dp))
                Switch(checked = isAlcoholic, onCheckedChange = { isAlcoholic = it })
                FavoriteButton(isFavorite = isFavorite, onFavoriteChange = {
                    isFavorite = it
                })
            }

            OutlinedTextField(value = glass,
                onValueChange = { glass = it },
                label = { Text("Glass Type") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(
                "Ingredients and Measures",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            ingredients.forEachIndexed { index, ingredient ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(value = ingredient,
                        onValueChange = { newValue ->
                            ingredients = ingredients.toMutableList().also {
                                it[index] = newValue
                            }
                        },
                        label = { Text("Ingredient ${index + 1}") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    OutlinedTextField(value = measures[index],
                        onValueChange = { newValue ->
                            measures = measures.toMutableList().also {
                                it[index] = newValue
                            }
                        },
                        label = { Text("Measure ${index + 1}") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun FavoriteButton(isFavorite: Boolean, onFavoriteChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(onClick = { onFavoriteChange(!isFavorite) }) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "Favorite",
                modifier = Modifier
                    .size(42.dp)
                    .background(Color.Black)
                    .clip(CircleShape),
                tint = if (isFavorite) Color.Yellow else MaterialTheme.colorScheme.onSurface
            )
        }
        Text("Favorite")
    }
}


@Composable
fun GalleryPickerButton(onGalleryPick: (Uri?) -> Unit) {
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onGalleryPick(uri)
    }

    OutlinedButton(onClick = { galleryLauncher.launch("image/*") }) {
        Icon(Icons.Filled.AddAPhoto, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Choose from gallery")
    }
}



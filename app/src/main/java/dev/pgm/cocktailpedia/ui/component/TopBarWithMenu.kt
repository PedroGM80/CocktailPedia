package dev.pgm.cocktailpedia.ui.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import dev.pgm.cocktailpedia.R
import dev.pgm.cocktailpedia.ui.screen.navigation.ScreenAbout
import dev.pgm.cocktailpedia.ui.screen.navigation.ScreenAddCocktail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithMenu(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorScheme.primary
        ),
        title = { Text(stringResource(R.string.app_name), color = colorScheme.onPrimary) },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    Icons.Default.MoreVert, contentDescription = stringResource(R.string.menu),
                    tint = colorScheme.onPrimary
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.add_cocktail)) },
                    onClick = {
                        expanded = false
                        navController.navigate(ScreenAddCocktail)
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.about_us)) },
                    onClick = {
                        expanded = false
                        navController.navigate(ScreenAbout)
                    }
                )
            }
        },
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
    )
}

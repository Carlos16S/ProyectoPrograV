package com.notes.notes.ui.theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Terracota,
    secondary = VerdeMusgo,
    background = Negro,
    surface = Negro,
    onPrimary = Blanco,
    onSecondary = Blanco,
    onBackground = Blanco,
    onSurface = Blanco,
)

private val LightColorScheme = lightColorScheme(
    primary = Terracota,
    secondary = VerdeMusgo,
    background = Blanco,
    surface = Blanco,
    onPrimary = Blanco,
    onSecondary = Blanco,
    onBackground = Negro,
    onSurface = Negro,
)

@Composable
fun NotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // desactivamos dynamic para usar tu paleta
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
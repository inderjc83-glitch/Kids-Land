package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SunshineYellow,
    onPrimary = Color(0xFF1A1A1A),
    primaryContainer = Color(0xFF263353),
    onPrimaryContainer = SunshineYellow,
    secondary = SkyBlue,
    onSecondary = Color(0xFF0D1B2A),
    secondaryContainer = Color(0xFF1E2D4A),
    onSecondaryContainer = SkyBlue,
    tertiary = NeonPink,
    onTertiary = Color.White,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkCardBorder
)

private val LightColorScheme = lightColorScheme(
    primary = SkyBlue,
    onPrimary = Color.White,
    primaryContainer = SunshineYellow,
    onPrimaryContainer = Color.Black,
    secondary = GrassGreen,
    onSecondary = Color.White,
    secondaryContainer = BrightOrange,
    onSecondaryContainer = Color.White,
    tertiary = BerryPurple,
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,
    outline = LightCardBorder
)

@Composable
fun KidsLandTheme(
    darkTheme: Boolean = true, // Default to dark mode as requested
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

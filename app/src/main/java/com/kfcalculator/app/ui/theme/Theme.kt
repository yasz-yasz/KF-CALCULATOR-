package com.kfcalculator.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val SiaNavy = Color(0xFF0B1F3A)
val SiaNavyLight = Color(0xFF1A365D)
val SiaGold = Color(0xFFC8A055)
val SiaGoldSoft = Color(0xFFF6ECD8)
val SiaGoldDark = Color(0xFF8C6B28)
val SurfaceLight = Color(0xFFF5F7FA)
val CardLight = Color(0xFFFFFFFF)
val TextPrimaryLight = Color(0xFF111827)
val TextSecondaryLight = Color(0xFF5A6578)
val SuccessGreen = Color(0xFF15803D)
val SuccessGreenSoft = Color(0xFFDCFCE7)
val WarningAmber = Color(0xFFB45309)
val WarningAmberSoft = Color(0xFFFEF3C7)

private val LightColors = lightColorScheme(
    primary = SiaNavy,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCE6F7),
    onPrimaryContainer = SiaNavy,
    secondary = SiaGold,
    onSecondary = SiaNavy,
    secondaryContainer = SiaGoldSoft,
    onSecondaryContainer = SiaGoldDark,
    background = SurfaceLight,
    onBackground = TextPrimaryLight,
    surface = CardLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFE9EEF5),
    onSurfaceVariant = TextSecondaryLight,
    outline = Color(0xFFD0D8E4)
)

private val DarkColors = darkColorScheme(
    primary = SiaGold,
    onPrimary = SiaNavy,
    primaryContainer = SiaNavyLight,
    onPrimaryContainer = SiaGoldSoft,
    secondary = Color(0xFF7FA6DC),
    onSecondary = SiaNavy,
    secondaryContainer = Color(0xFF1E2D45),
    onSecondaryContainer = Color(0xFFDCE6F7),
    background = Color(0xFF091322),
    onBackground = Color(0xFFF1F5F9),
    surface = Color(0xFF101D32),
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF192944),
    onSurfaceVariant = Color(0xFFB0BDD1),
    outline = Color(0xFF2B3E5E)
)

@Composable
fun KFCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

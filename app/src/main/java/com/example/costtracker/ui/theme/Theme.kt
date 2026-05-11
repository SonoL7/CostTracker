package com.example.costtracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CuteLightColorScheme = lightColorScheme(
    primary = PinkPrimary,
    onPrimary = PinkOnPrimary,
    primaryContainer = PinkPrimaryContainer,
    onPrimaryContainer = PinkOnPrimaryContainer,
    secondary = LavenderSecondary,
    onSecondary = LavenderOnSecondary,
    secondaryContainer = LavenderSecondaryContainer,
    onSecondaryContainer = LavenderOnSecondaryContainer,
    tertiary = MintTertiary,
    onTertiary = MintOnTertiary,
    tertiaryContainer = MintTertiaryContainer,
    surface = WarmSurface,
    onSurface = WarmOnSurface,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = WarmOnSurfaceVariant,
    background = CreamBackground,
    onBackground = WarmOnSurface,
    error = SoftError,
    onError = SoftOnError,
    errorContainer = SoftErrorContainer,
    outline = PinkPrimary.copy(alpha = 0.3f),
    outlineVariant = LavenderSecondary.copy(alpha = 0.2f)
)

@Composable
fun CostTrackerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CuteLightColorScheme,
        typography = CuteTypography,
        content = content
    )
}

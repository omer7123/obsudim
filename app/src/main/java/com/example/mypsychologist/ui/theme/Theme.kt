package com.example.compose
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.mypsychologist.ui.theme.AppTypography
import com.example.mypsychologist.ui.theme.backgroundDark
import com.example.mypsychologist.ui.theme.backgroundDarkHighContrast
import com.example.mypsychologist.ui.theme.backgroundDarkMediumContrast
import com.example.mypsychologist.ui.theme.backgroundLight
import com.example.mypsychologist.ui.theme.backgroundLightHighContrast
import com.example.mypsychologist.ui.theme.backgroundLightMediumContrast
import com.example.mypsychologist.ui.theme.errorContainerDark
import com.example.mypsychologist.ui.theme.errorContainerDarkHighContrast
import com.example.mypsychologist.ui.theme.errorContainerDarkMediumContrast
import com.example.mypsychologist.ui.theme.errorContainerLight
import com.example.mypsychologist.ui.theme.errorContainerLightHighContrast
import com.example.mypsychologist.ui.theme.errorContainerLightMediumContrast
import com.example.mypsychologist.ui.theme.errorDark
import com.example.mypsychologist.ui.theme.errorDarkHighContrast
import com.example.mypsychologist.ui.theme.errorDarkMediumContrast
import com.example.mypsychologist.ui.theme.errorLight
import com.example.mypsychologist.ui.theme.errorLightHighContrast
import com.example.mypsychologist.ui.theme.errorLightMediumContrast
import com.example.mypsychologist.ui.theme.inverseOnSurfaceDark
import com.example.mypsychologist.ui.theme.inverseOnSurfaceDarkHighContrast
import com.example.mypsychologist.ui.theme.inverseOnSurfaceDarkMediumContrast
import com.example.mypsychologist.ui.theme.inverseOnSurfaceLight
import com.example.mypsychologist.ui.theme.inverseOnSurfaceLightHighContrast
import com.example.mypsychologist.ui.theme.inverseOnSurfaceLightMediumContrast
import com.example.mypsychologist.ui.theme.inversePrimaryDark
import com.example.mypsychologist.ui.theme.inversePrimaryDarkHighContrast
import com.example.mypsychologist.ui.theme.inversePrimaryDarkMediumContrast
import com.example.mypsychologist.ui.theme.inversePrimaryLight
import com.example.mypsychologist.ui.theme.inversePrimaryLightHighContrast
import com.example.mypsychologist.ui.theme.inversePrimaryLightMediumContrast
import com.example.mypsychologist.ui.theme.inverseSurfaceDark
import com.example.mypsychologist.ui.theme.inverseSurfaceDarkHighContrast
import com.example.mypsychologist.ui.theme.inverseSurfaceDarkMediumContrast
import com.example.mypsychologist.ui.theme.inverseSurfaceLight
import com.example.mypsychologist.ui.theme.inverseSurfaceLightHighContrast
import com.example.mypsychologist.ui.theme.inverseSurfaceLightMediumContrast
import com.example.mypsychologist.ui.theme.onBackgroundDark
import com.example.mypsychologist.ui.theme.onBackgroundDarkHighContrast
import com.example.mypsychologist.ui.theme.onBackgroundDarkMediumContrast
import com.example.mypsychologist.ui.theme.onBackgroundLight
import com.example.mypsychologist.ui.theme.onBackgroundLightHighContrast
import com.example.mypsychologist.ui.theme.onBackgroundLightMediumContrast
import com.example.mypsychologist.ui.theme.onErrorContainerDark
import com.example.mypsychologist.ui.theme.onErrorContainerDarkHighContrast
import com.example.mypsychologist.ui.theme.onErrorContainerDarkMediumContrast
import com.example.mypsychologist.ui.theme.onErrorContainerLight
import com.example.mypsychologist.ui.theme.onErrorContainerLightHighContrast
import com.example.mypsychologist.ui.theme.onErrorContainerLightMediumContrast
import com.example.mypsychologist.ui.theme.onErrorDark
import com.example.mypsychologist.ui.theme.onErrorDarkHighContrast
import com.example.mypsychologist.ui.theme.onErrorDarkMediumContrast
import com.example.mypsychologist.ui.theme.onErrorLight
import com.example.mypsychologist.ui.theme.onErrorLightHighContrast
import com.example.mypsychologist.ui.theme.onErrorLightMediumContrast
import com.example.mypsychologist.ui.theme.onPrimaryContainerDark
import com.example.mypsychologist.ui.theme.onPrimaryContainerDarkHighContrast
import com.example.mypsychologist.ui.theme.onPrimaryContainerDarkMediumContrast
import com.example.mypsychologist.ui.theme.onPrimaryContainerLight
import com.example.mypsychologist.ui.theme.onPrimaryContainerLightHighContrast
import com.example.mypsychologist.ui.theme.onPrimaryContainerLightMediumContrast
import com.example.mypsychologist.ui.theme.onPrimaryDark
import com.example.mypsychologist.ui.theme.onPrimaryDarkHighContrast
import com.example.mypsychologist.ui.theme.onPrimaryDarkMediumContrast
import com.example.mypsychologist.ui.theme.onPrimaryLight
import com.example.mypsychologist.ui.theme.onPrimaryLightHighContrast
import com.example.mypsychologist.ui.theme.onPrimaryLightMediumContrast
import com.example.mypsychologist.ui.theme.onSecondaryContainerDark
import com.example.mypsychologist.ui.theme.onSecondaryContainerDarkHighContrast
import com.example.mypsychologist.ui.theme.onSecondaryContainerDarkMediumContrast
import com.example.mypsychologist.ui.theme.onSecondaryContainerLight
import com.example.mypsychologist.ui.theme.onSecondaryContainerLightHighContrast
import com.example.mypsychologist.ui.theme.onSecondaryContainerLightMediumContrast
import com.example.mypsychologist.ui.theme.onSecondaryDark
import com.example.mypsychologist.ui.theme.onSecondaryDarkHighContrast
import com.example.mypsychologist.ui.theme.onSecondaryDarkMediumContrast
import com.example.mypsychologist.ui.theme.onSecondaryLight
import com.example.mypsychologist.ui.theme.onSecondaryLightHighContrast
import com.example.mypsychologist.ui.theme.onSecondaryLightMediumContrast
import com.example.mypsychologist.ui.theme.onSurfaceDark
import com.example.mypsychologist.ui.theme.onSurfaceDarkHighContrast
import com.example.mypsychologist.ui.theme.onSurfaceDarkMediumContrast
import com.example.mypsychologist.ui.theme.onSurfaceLight
import com.example.mypsychologist.ui.theme.onSurfaceLightHighContrast
import com.example.mypsychologist.ui.theme.onSurfaceLightMediumContrast
import com.example.mypsychologist.ui.theme.onSurfaceVariantDark
import com.example.mypsychologist.ui.theme.onSurfaceVariantDarkHighContrast
import com.example.mypsychologist.ui.theme.onSurfaceVariantDarkMediumContrast
import com.example.mypsychologist.ui.theme.onSurfaceVariantLight
import com.example.mypsychologist.ui.theme.onSurfaceVariantLightHighContrast
import com.example.mypsychologist.ui.theme.onSurfaceVariantLightMediumContrast
import com.example.mypsychologist.ui.theme.onTertiaryContainerDark
import com.example.mypsychologist.ui.theme.onTertiaryContainerDarkHighContrast
import com.example.mypsychologist.ui.theme.onTertiaryContainerDarkMediumContrast
import com.example.mypsychologist.ui.theme.onTertiaryContainerLight
import com.example.mypsychologist.ui.theme.onTertiaryContainerLightHighContrast
import com.example.mypsychologist.ui.theme.onTertiaryContainerLightMediumContrast
import com.example.mypsychologist.ui.theme.onTertiaryDark
import com.example.mypsychologist.ui.theme.onTertiaryDarkHighContrast
import com.example.mypsychologist.ui.theme.onTertiaryDarkMediumContrast
import com.example.mypsychologist.ui.theme.onTertiaryLight
import com.example.mypsychologist.ui.theme.onTertiaryLightHighContrast
import com.example.mypsychologist.ui.theme.onTertiaryLightMediumContrast
import com.example.mypsychologist.ui.theme.outlineDark
import com.example.mypsychologist.ui.theme.outlineDarkHighContrast
import com.example.mypsychologist.ui.theme.outlineDarkMediumContrast
import com.example.mypsychologist.ui.theme.outlineLight
import com.example.mypsychologist.ui.theme.outlineLightHighContrast
import com.example.mypsychologist.ui.theme.outlineLightMediumContrast
import com.example.mypsychologist.ui.theme.outlineVariantDark
import com.example.mypsychologist.ui.theme.outlineVariantDarkHighContrast
import com.example.mypsychologist.ui.theme.outlineVariantDarkMediumContrast
import com.example.mypsychologist.ui.theme.outlineVariantLight
import com.example.mypsychologist.ui.theme.outlineVariantLightHighContrast
import com.example.mypsychologist.ui.theme.outlineVariantLightMediumContrast
import com.example.mypsychologist.ui.theme.primaryContainerDark
import com.example.mypsychologist.ui.theme.primaryContainerDarkHighContrast
import com.example.mypsychologist.ui.theme.primaryContainerDarkMediumContrast
import com.example.mypsychologist.ui.theme.primaryContainerLight
import com.example.mypsychologist.ui.theme.primaryContainerLightHighContrast
import com.example.mypsychologist.ui.theme.primaryContainerLightMediumContrast
import com.example.mypsychologist.ui.theme.primaryDark
import com.example.mypsychologist.ui.theme.primaryDarkHighContrast
import com.example.mypsychologist.ui.theme.primaryDarkMediumContrast
import com.example.mypsychologist.ui.theme.primaryLight
import com.example.mypsychologist.ui.theme.primaryLightHighContrast
import com.example.mypsychologist.ui.theme.primaryLightMediumContrast
import com.example.mypsychologist.ui.theme.scrimDark
import com.example.mypsychologist.ui.theme.scrimDarkHighContrast
import com.example.mypsychologist.ui.theme.scrimDarkMediumContrast
import com.example.mypsychologist.ui.theme.scrimLight
import com.example.mypsychologist.ui.theme.scrimLightHighContrast
import com.example.mypsychologist.ui.theme.scrimLightMediumContrast
import com.example.mypsychologist.ui.theme.secondaryContainerDark
import com.example.mypsychologist.ui.theme.secondaryContainerDarkHighContrast
import com.example.mypsychologist.ui.theme.secondaryContainerDarkMediumContrast
import com.example.mypsychologist.ui.theme.secondaryContainerLight
import com.example.mypsychologist.ui.theme.secondaryContainerLightHighContrast
import com.example.mypsychologist.ui.theme.secondaryContainerLightMediumContrast
import com.example.mypsychologist.ui.theme.secondaryDark
import com.example.mypsychologist.ui.theme.secondaryDarkHighContrast
import com.example.mypsychologist.ui.theme.secondaryDarkMediumContrast
import com.example.mypsychologist.ui.theme.secondaryLight
import com.example.mypsychologist.ui.theme.secondaryLightHighContrast
import com.example.mypsychologist.ui.theme.secondaryLightMediumContrast
import com.example.mypsychologist.ui.theme.surfaceBrightDark
import com.example.mypsychologist.ui.theme.surfaceBrightDarkHighContrast
import com.example.mypsychologist.ui.theme.surfaceBrightDarkMediumContrast
import com.example.mypsychologist.ui.theme.surfaceBrightLight
import com.example.mypsychologist.ui.theme.surfaceBrightLightHighContrast
import com.example.mypsychologist.ui.theme.surfaceBrightLightMediumContrast
import com.example.mypsychologist.ui.theme.surfaceContainerDark
import com.example.mypsychologist.ui.theme.surfaceContainerDarkHighContrast
import com.example.mypsychologist.ui.theme.surfaceContainerDarkMediumContrast
import com.example.mypsychologist.ui.theme.surfaceContainerHighDark
import com.example.mypsychologist.ui.theme.surfaceContainerHighDarkHighContrast
import com.example.mypsychologist.ui.theme.surfaceContainerHighDarkMediumContrast
import com.example.mypsychologist.ui.theme.surfaceContainerHighLight
import com.example.mypsychologist.ui.theme.surfaceContainerHighLightHighContrast
import com.example.mypsychologist.ui.theme.surfaceContainerHighLightMediumContrast
import com.example.mypsychologist.ui.theme.surfaceContainerHighestDark
import com.example.mypsychologist.ui.theme.surfaceContainerHighestDarkHighContrast
import com.example.mypsychologist.ui.theme.surfaceContainerHighestDarkMediumContrast
import com.example.mypsychologist.ui.theme.surfaceContainerHighestLight
import com.example.mypsychologist.ui.theme.surfaceContainerHighestLightHighContrast
import com.example.mypsychologist.ui.theme.surfaceContainerHighestLightMediumContrast
import com.example.mypsychologist.ui.theme.surfaceContainerLight
import com.example.mypsychologist.ui.theme.surfaceContainerLightHighContrast
import com.example.mypsychologist.ui.theme.surfaceContainerLightMediumContrast
import com.example.mypsychologist.ui.theme.surfaceContainerLowDark
import com.example.mypsychologist.ui.theme.surfaceContainerLowDarkHighContrast
import com.example.mypsychologist.ui.theme.surfaceContainerLowDarkMediumContrast
import com.example.mypsychologist.ui.theme.surfaceContainerLowLight
import com.example.mypsychologist.ui.theme.surfaceContainerLowLightHighContrast
import com.example.mypsychologist.ui.theme.surfaceContainerLowLightMediumContrast
import com.example.mypsychologist.ui.theme.surfaceContainerLowestDark
import com.example.mypsychologist.ui.theme.surfaceContainerLowestDarkHighContrast
import com.example.mypsychologist.ui.theme.surfaceContainerLowestDarkMediumContrast
import com.example.mypsychologist.ui.theme.surfaceContainerLowestLight
import com.example.mypsychologist.ui.theme.surfaceContainerLowestLightHighContrast
import com.example.mypsychologist.ui.theme.surfaceContainerLowestLightMediumContrast
import com.example.mypsychologist.ui.theme.surfaceDark
import com.example.mypsychologist.ui.theme.surfaceDarkHighContrast
import com.example.mypsychologist.ui.theme.surfaceDarkMediumContrast
import com.example.mypsychologist.ui.theme.surfaceDimDark
import com.example.mypsychologist.ui.theme.surfaceDimDarkHighContrast
import com.example.mypsychologist.ui.theme.surfaceDimDarkMediumContrast
import com.example.mypsychologist.ui.theme.surfaceDimLight
import com.example.mypsychologist.ui.theme.surfaceDimLightHighContrast
import com.example.mypsychologist.ui.theme.surfaceDimLightMediumContrast
import com.example.mypsychologist.ui.theme.surfaceLight
import com.example.mypsychologist.ui.theme.surfaceLightHighContrast
import com.example.mypsychologist.ui.theme.surfaceLightMediumContrast
import com.example.mypsychologist.ui.theme.surfaceVariantDark
import com.example.mypsychologist.ui.theme.surfaceVariantDarkHighContrast
import com.example.mypsychologist.ui.theme.surfaceVariantDarkMediumContrast
import com.example.mypsychologist.ui.theme.surfaceVariantLight
import com.example.mypsychologist.ui.theme.surfaceVariantLightHighContrast
import com.example.mypsychologist.ui.theme.surfaceVariantLightMediumContrast
import com.example.mypsychologist.ui.theme.tertiaryContainerDark
import com.example.mypsychologist.ui.theme.tertiaryContainerDarkHighContrast
import com.example.mypsychologist.ui.theme.tertiaryContainerDarkMediumContrast
import com.example.mypsychologist.ui.theme.tertiaryContainerLight
import com.example.mypsychologist.ui.theme.tertiaryContainerLightHighContrast
import com.example.mypsychologist.ui.theme.tertiaryContainerLightMediumContrast
import com.example.mypsychologist.ui.theme.tertiaryDark
import com.example.mypsychologist.ui.theme.tertiaryDarkHighContrast
import com.example.mypsychologist.ui.theme.tertiaryDarkMediumContrast
import com.example.mypsychologist.ui.theme.tertiaryLight
import com.example.mypsychologist.ui.theme.tertiaryLightHighContrast
import com.example.mypsychologist.ui.theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}


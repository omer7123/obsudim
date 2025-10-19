package com.obsudim.mypsychologist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkShema
        else -> lightShema
    }

    CompositionLocalProvider(
        LocalAppThemeColor provides colorScheme,
        content = content
    )
}



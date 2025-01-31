package com.example.mypsychologist.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


data class AppThemeColor(
    //Font
    val primaryText: Color, // черный 100
    val primaryTextInvert: Color, // белый 100
    val secondaryText: Color, //черный 60
    //Background
    val primaryBackground: Color, // синий 80
    val secondaryBackground: Color, // синий 30
    val tertiaryBackground: Color, // белый 70
    val fourthBackground: Color, // синий 20
    val screenBackground: Color, // белый 100
    val navBackground: Color, // синий 100
)

val lightShema = AppThemeColor(
    primaryText = Color(0xFF1C1A1A),
    primaryTextInvert = Color(0xFFFFFFFF), // белый 100
    secondaryText = Color(0xFF545454),  //черный 60
    //Background
    primaryBackground = Color(0xFF3555D4), // синий 80
    secondaryBackground = Color(0xFFEBEEFF), // синий 30
    tertiaryBackground = Color(0xFFF1F3F6), // белый 70
    fourthBackground = Color(0xFFF3F5FF), // синий 20
    screenBackground = Color(0xFFFFFFFF), // белый 100
    navBackground = Color(0xFF0C2A9F), // синий 100
)

val darkShema = AppThemeColor(
    primaryText = Color(0xFFFFFFFF),
    primaryTextInvert = Color(0xFF2F2D2D),// белый 100
    secondaryText = Color(0xFFF1F3F6), //черный 60
    //Background
    primaryBackground = Color(0xFFEBEEFF), // синий 80
    secondaryBackground = Color(0xFF1C1A1A), // синий 30
    tertiaryBackground = Color(0xFF545454), // белый 70
    fourthBackground = Color(0xFF1C1A1A), // синий 20
    screenBackground = Color(0xFF2F2D2D), // белый 100
    navBackground = Color(0xFFF3F5FF), // синий 100
)

object AppTheme {
    val colors: AppThemeColor
        @Composable get() = LocalAppThemeColor.current

    val typography: AppTypography
        get() = appTypography
}

val LocalAppThemeColor = staticCompositionLocalOf<AppThemeColor> {
    error("No colors provided")
}





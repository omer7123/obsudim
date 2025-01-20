package com.example.mypsychologist.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mypsychologist.R

data class AppTypography(
    val titleCygreFont: TextStyle,
    val titleXS: TextStyle,
    val bodyXLBold: TextStyle,
    val bodyXL: TextStyle,
    val bodyLBold: TextStyle,
    val bodyL: TextStyle,
    val bodyMBold: TextStyle,
    val bodyM: TextStyle,
    val bodySBold: TextStyle,
    val bodyS: TextStyle,
)

val appTypography = AppTypography(
    titleCygreFont = TextStyle(
        fontFamily = FontFamily(Font(R.font.cygre_semi_bold)),
        fontWeight = FontWeight(600),
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    titleXS = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontWeight = FontWeight(700),
        fontSize = 24.sp,
        lineHeight = 30.sp,
    ),
    bodyXLBold = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontWeight = FontWeight(700),
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    bodyXL = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight(500),
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    bodyLBold = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontWeight = FontWeight(700),
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    bodyL = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    bodyMBold = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontWeight = FontWeight(700),
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    bodyM = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    bodySBold = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_semibold)),
        fontWeight = FontWeight(600),
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    bodyS = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight(500),
        fontSize = 12.sp,
        lineHeight = 16.sp,
    )
)
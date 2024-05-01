package com.lumins.sua.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.lumins.sua.R


val biryaniFontFamily = FontFamily(
    Font(resId = R.font.biryani_black, weight = FontWeight.Black),
    Font(resId = R.font.biryani_extrabold, weight = FontWeight.ExtraBold),
    Font(resId = R.font.biryani_semibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.biryani_bold, weight = FontWeight.Bold),
    Font(resId = R.font.biryani, weight = FontWeight.Normal),
    Font(resId = R.font.biryani_light, weight = FontWeight.Light),
    Font(resId = R.font.biryani_extralight, weight = FontWeight.ExtraLight),
)

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = biryaniFontFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = biryaniFontFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = biryaniFontFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = biryaniFontFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = biryaniFontFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = biryaniFontFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = biryaniFontFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = biryaniFontFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = biryaniFontFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = biryaniFontFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = biryaniFontFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = biryaniFontFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = biryaniFontFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = biryaniFontFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = biryaniFontFamily)
)
package com.lumins.sua.ui.theme

import androidx.compose.ui.graphics.Color
import kotlin.random.Random


fun generateRandomBrightColor(isDarkTheme: Boolean): Color {
    // Generate random bright RGB values
    val rangeStart = if (isDarkTheme) 0 else 128
    val rangeEnd = if (isDarkTheme) 128 else 256
    val red = Random.nextInt(rangeStart, rangeEnd)
    val green = Random.nextInt(rangeStart, rangeEnd)
    val blue = Random.nextInt(rangeStart, rangeEnd)

    return Color(red = red, green = green, blue = blue)
}


object SuaColors {
    val primaryBlue = Color(0xFF37479C)
    val primaryBlueVariant = Color(0xFF3971FF)
    val onPrimary = Color.White
    val darkRedMisc = Color(0xFFCB1313)
    val lightGreenMisc = Color(0xFF68DC3E)
    val darkGrayMisc = Color(0xFF818075)
    val altBackground = Color(0xFFF5FDBD)
    val darkGreyBackground = Color(0xFF686C6B)
    val greyBackground = Color.Gray
    val lightGreyBackground = Color.LightGray
    val goldenYellow = Color(0xFFFED826)
}


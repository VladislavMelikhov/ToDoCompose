package com.example.to_docompose.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val HoloGreen = Color(0xFF00C980)
val HoloYellow = Color(0xFFFFC114)
val HoloRed = Color(0xFFFF4646)
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

@Immutable
data class CustomColorsPalette(
    val topAppBarBackgroundColor: Color = Color.Unspecified,
    val topAppBarContentColor: Color = Color.Unspecified,
) {
    val statusBarColor: Color
        get() = topAppBarBackgroundColor
}

val LightCustomColorsPalette = CustomColorsPalette(
    topAppBarBackgroundColor = Purple40,
    topAppBarContentColor = White,
)

val DarkCustomColorsPalette = CustomColorsPalette(
    topAppBarBackgroundColor = Black,
    topAppBarContentColor = LightGray,
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }

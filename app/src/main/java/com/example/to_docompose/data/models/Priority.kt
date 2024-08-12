package com.example.to_docompose.data.models

import androidx.compose.ui.graphics.Color
import com.example.to_docompose.ui.theme.HoloGreen
import com.example.to_docompose.ui.theme.HoloRed
import com.example.to_docompose.ui.theme.HoloYellow

enum class Priority(
    val color: Color
) {
    HIGH(
        color = HoloRed,
    ),
    MEDIUM(
        color = HoloYellow,
    ),
    LOW(
        color = HoloGreen,
    ),
}

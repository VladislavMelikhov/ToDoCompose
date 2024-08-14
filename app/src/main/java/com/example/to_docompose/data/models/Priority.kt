package com.example.to_docompose.data.models

import androidx.compose.ui.graphics.Color
import com.example.to_docompose.ui.theme.HoloGreen
import com.example.to_docompose.ui.theme.HoloRed
import com.example.to_docompose.ui.theme.HoloYellow

enum class Priority(
    val id: Int,
    val color: Color
) {
    HIGH(
        id = 0,
        color = HoloRed,
    ),
    MEDIUM(
        id = 1,
        color = HoloYellow,
    ),
    LOW(
        id = 2,
        color = HoloGreen,
    );

    companion object {

        private val valuesByIds: Map<Int, Priority> =
            entries.associateBy(Priority::id)

        fun fromId(id: Int): Priority =
            valuesByIds[id] ?: MEDIUM
    }
}

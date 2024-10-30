package com.example.to_docompose.data.models

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.to_docompose.R
import com.example.to_docompose.ui.theme.HoloGreen
import com.example.to_docompose.ui.theme.HoloRed
import com.example.to_docompose.ui.theme.HoloYellow

enum class Priority(
    val id: Int,
    val importance: Int,
    val color: Color,
    @StringRes
    val titleId: Int,
) {
    HIGH(
        id = 0,
        importance = 100,
        color = HoloRed,
        titleId = R.string.high,
    ),
    MEDIUM(
        id = 1,
        importance = 101,
        color = HoloYellow,
        titleId = R.string.medium,
    ),
    LOW(
        id = 2,
        importance = 102,
        color = HoloGreen,
        titleId = R.string.low,
    );

    companion object {

        private val valuesByIds: Map<Int, Priority> =
            entries.associateBy(Priority::id)

        fun fromId(id: Int): Priority =
            valuesByIds[id] ?: MEDIUM
    }
}

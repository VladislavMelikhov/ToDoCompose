package com.example.to_docompose.data.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.to_docompose.R

enum class TasksSortPolicy(
    val id: Int,
    @DrawableRes
    val iconId: Int,
    @StringRes
    val titleId: Int,
) {
    HIGHEST_PRIORITY(
        id = 0,
        iconId = R.drawable.ic_keyboard_arrow_up,
        titleId = R.string.highest_priority,
    ),
    LOWEST_PRIORITY(
        id = 1,
        iconId = R.drawable.ic_keyboard_arrow_down,
        titleId = R.string.lowest_priority,
    ),
    DEFAULT(
        id = 2,
        iconId = R.drawable.ic_filter_list,
        titleId = R.string.default_order,
    ),
}

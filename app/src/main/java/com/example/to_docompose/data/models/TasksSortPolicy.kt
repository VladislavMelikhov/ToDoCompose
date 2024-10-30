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
    val comparator: Comparator<ToDoTask>,
) {
    HIGHEST_PRIORITY(
        id = 0,
        iconId = R.drawable.ic_keyboard_arrow_up,
        titleId = R.string.highest_priority,
        comparator = compareBy(ToDoTask::importance)
    ),
    LOWEST_PRIORITY(
        id = 1,
        iconId = R.drawable.ic_keyboard_arrow_down,
        titleId = R.string.lowest_priority,
        comparator = compareByDescending(ToDoTask::importance)
    ),
    DEFAULT(
        id = 2,
        iconId = R.drawable.ic_filter_list,
        titleId = R.string.default_order,
        comparator = compareBy(ToDoTask::id)
    );

    companion object {

        private val valuesByIds: Map<Int, TasksSortPolicy> =
            TasksSortPolicy.entries.associateBy(TasksSortPolicy::id)

        fun fromId(id: Int): TasksSortPolicy =
            valuesByIds[id] ?: DEFAULT
    }
}

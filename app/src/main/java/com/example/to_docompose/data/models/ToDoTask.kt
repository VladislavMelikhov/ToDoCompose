package com.example.to_docompose.data.models

data class ToDoTask(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority,
) {

    val importance: Int
        get() = priority.importance

    companion object {

        fun empty(id: Int = 0): ToDoTask =
            ToDoTask(
                id = id,
                title = "",
                description = "",
                priority = Priority.MEDIUM,
            )
    }
}

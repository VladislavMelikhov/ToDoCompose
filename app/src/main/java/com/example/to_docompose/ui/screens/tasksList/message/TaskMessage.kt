package com.example.to_docompose.ui.screens.tasksList.message

import android.content.Context
import com.example.to_docompose.R
import com.example.to_docompose.data.models.ToDoTask

sealed interface TaskMessage {

    data class TaskAdded(
        val taskTitle: String,
    ) : TaskMessage

    data class TaskUpdated(
        val updatedTaskTitle: String,
    ) : TaskMessage

    data class TasksDeleted(
        val tasks: List<ToDoTask>,
    ) : TaskMessage

    data class TasksRestored(
        val tasks: List<ToDoTask>,
    ) : TaskMessage
}

fun TaskMessage.toDisplayString(context: Context): String =
    when (this) {
        is TaskMessage.TaskAdded -> context.getString(R.string.task_added, taskTitle)
        is TaskMessage.TaskUpdated -> context.getString(R.string.task_updated, updatedTaskTitle)
        is TaskMessage.TasksDeleted -> {
            if (tasks.size == 1) {
                context.getString(R.string.task_deleted, tasks.single().title)
            } else {
                context.getString(R.string.tasks_deleted, tasks.size)
            }
        }
        is TaskMessage.TasksRestored -> {
            if (tasks.size == 1) {
                context.getString(R.string.task_restored, tasks.single().title)
            } else {
                context.getString(R.string.tasks_restored, tasks.size)
            }
        }
    }

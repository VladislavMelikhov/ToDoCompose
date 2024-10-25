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

    data class TaskDeleted(
        val originalTask: ToDoTask,
    ) : TaskMessage

    data class TaskRestored(
        val taskTitle: String,
    ) : TaskMessage
}

fun TaskMessage.toDisplayString(context: Context): String =
    when (this) {
        is TaskMessage.TaskAdded -> context.getString(R.string.task_added, taskTitle)
        is TaskMessage.TaskUpdated -> context.getString(R.string.task_updated, updatedTaskTitle)
        is TaskMessage.TaskDeleted -> context.getString(R.string.task_deleted, originalTask.title)
        is TaskMessage.TaskRestored -> context.getString(R.string.task_restored, taskTitle)
    }

package com.example.to_docompose.ui.screens.taskDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.navigation.ToDoTaskAction
import com.example.to_docompose.utils.ToastManager

const val TASK_DETAILS_ARG_KEY = "taskId"

@Composable
fun TaskDetailsScreen(
    viewModel: TaskDetailsViewModel = hiltViewModel(),
    navigateToTasksList: (ToDoTaskAction) -> Unit,
) {
    val selectedTask by viewModel.selectedTask.collectAsState()

    val editedTitle by viewModel.editedTitle.collectAsState()
    val editedDescription by viewModel.editedDescription.collectAsState()
    val editedPriority by viewModel.editedPriority.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskDetailsAppBar(
                selectedTask = selectedTask,
                navigateToTasksList = { action ->
                    if (!needToValidateFields(action)) {
                        navigateToTasksList(action)
                        return@TaskDetailsAppBar
                    }

                    when (val validationResult = viewModel.validateFields()) {
                        is ValidationResult.Error -> {
                            ToastManager.showShort(context, validationResult.messageId)
                        }
                        is ValidationResult.Success -> {
                            navigateToTasksList(action)
                        }
                    }

                    when (action) {
                        ToDoTaskAction.ADD -> {
                            val newTask = ToDoTask(
                                id = 0,
                                title = editedTitle,
                                description = editedDescription,
                                priority = editedPriority,
                            )
                            viewModel.addTask(newTask)
                        }
                        else -> {}
                    }
                },
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                TaskDetailsContent(
                    title = editedTitle,
                    onTitleChange = viewModel::setEditedTitle,
                    description = editedDescription,
                    onDescriptionChange = viewModel::setEditedDescription,
                    priority = editedPriority,
                    onPrioritySelected = viewModel::setEditedPriority,
                )
            }
        },
    )
}

private fun needToValidateFields(action: ToDoTaskAction): Boolean {
    return action == ToDoTaskAction.ADD ||
            action == ToDoTaskAction.UPDATE
}

package com.example.to_docompose.ui.screens.taskDetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.to_docompose.R
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.components.ConfirmationDialog
import com.example.to_docompose.utils.ToastManager

const val TASK_DETAILS_ARG_KEY = "taskId"

@Composable
fun TaskDetailsScreen(
    viewModel: TaskDetailsViewModel = hiltViewModel(),
    navigateToTasksList: () -> Unit,
) {
    val selectedTask by viewModel.selectedTask.collectAsStateWithLifecycle()

    val editedTitle by viewModel.editedTitle.collectAsStateWithLifecycle()
    val editedDescription by viewModel.editedDescription.collectAsStateWithLifecycle()
    val editedPriority by viewModel.editedPriority.collectAsStateWithLifecycle()

    val deleteConfirmationDialogState by viewModel.deleteConfirmationDialogState.collectAsStateWithLifecycle()
    val exitConfirmationDialogState by viewModel.exitConfirmationDialogState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    BackHandler(enabled = true) {
        viewModel.showExitConfirmationDialog()
    }

    Scaffold(
        topBar = {
            TaskDetailsAppBar(
                selectedTask = selectedTask,
                onBackClick = {
                    viewModel.showExitConfirmationDialog()
                },
                onAddClick = {
                    val newTask = ToDoTask(
                        id = 0,
                        title = editedTitle,
                        description = editedDescription,
                        priority = editedPriority,
                    )

                    val validationResult = validateFields(newTask)
                    if (validationResult is ValidationResult.Error) {
                        ToastManager.showShort(context, validationResult.messageId)
                        return@TaskDetailsAppBar
                    }

                    viewModel.addTask(newTask)
                    navigateToTasksList()
                },
                onCloseClick = {
                    viewModel.showExitConfirmationDialog()
                },
                onUpdateClick = { taskId ->
                    val editedTask = ToDoTask(
                        id = taskId,
                        title = editedTitle,
                        description = editedDescription,
                        priority = editedPriority,
                    )

                    val validationResult = validateFields(editedTask)
                    if (validationResult is ValidationResult.Error) {
                        ToastManager.showShort(context, validationResult.messageId)
                        return@TaskDetailsAppBar
                    }

                    if (editedTask != selectedTask) {
                        viewModel.updateTask(editedTask)
                    }
                    navigateToTasksList()
                },
                onDeleteClick = { originalTask ->
                    viewModel.showDeleteConfirmationDialog(originalTask)
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

    when (val state = deleteConfirmationDialogState) {
        is TaskConfirmationDialogState.Shown -> {
            val originalTask = state.task
            ConfirmationDialog(
                title = stringResource(R.string.confirm_delete_title),
                message = stringResource(R.string.confirm_delete_message),
                onActionConfirmed = {
                    viewModel.deleteTask(originalTask)
                    navigateToTasksList()
                },
                onCloseRequest = {
                    viewModel.hideDeleteConfirmationDialog()
                },
            )
        }
        is TaskConfirmationDialogState.Hidden -> {}
    }
    when (exitConfirmationDialogState) {
        is ConfirmationDialogState.Shown -> {
            ConfirmationDialog(
                title = stringResource(R.string.confirm_exit_title),
                message = stringResource(R.string.confirm_exit_message),
                onActionConfirmed = {
                    navigateToTasksList()
                },
                onCloseRequest = {
                    viewModel.hideExitConfirmationDialog()
                },
            )
        }
        is ConfirmationDialogState.Hidden -> {}
    }
}

private fun validateFields(
    task: ToDoTask,
): ValidationResult {
    val title = task.title
    if (title.isBlank()) {
        return ValidationResult.Error(R.string.title_is_empty)
    }

    val description = task.description
    if (description.isBlank()) {
        return ValidationResult.Error(R.string.description_is_empty)
    }

    return ValidationResult.Success
}

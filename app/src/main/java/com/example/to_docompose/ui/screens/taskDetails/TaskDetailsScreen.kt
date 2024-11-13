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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.to_docompose.R
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.components.ConfirmationDialog
import com.example.to_docompose.utils.ToastManager

@Composable
fun TaskDetailsScreen(
    viewModel: TaskDetailsViewModel = hiltViewModel(),
    navigateToTasksList: () -> Unit,
) {
    val originalTask by viewModel.originalTask.collectAsStateWithLifecycle()

    val editedTitle by viewModel.editedTitle.collectAsStateWithLifecycle()
    val editedDescription by viewModel.editedDescription.collectAsStateWithLifecycle()
    val editedPriority by viewModel.editedPriority.collectAsStateWithLifecycle()
    val editedTask by viewModel.editedTask.collectAsStateWithLifecycle()

    val exitConfirmationVisibility by viewModel.exitConfirmationVisibility
        .collectAsStateWithLifecycle(minActiveState = Lifecycle.State.CREATED)
    val deleteConfirmationVisibility by viewModel.deleteConfirmationVisibility
        .collectAsStateWithLifecycle(minActiveState = Lifecycle.State.CREATED)

    val context = LocalContext.current

    fun safeNavigateToTasksList() {
        if (editedTask != originalTask) {
            viewModel.showExitConfirmation()
        } else {
            navigateToTasksList()
        }
    }

    BackHandler {
        safeNavigateToTasksList()
    }

    Scaffold(
        topBar = {
            TaskDetailsAppBar(
                selectedTask = originalTask,
                onBackClick = {
                    safeNavigateToTasksList()
                },
                onAddClick = {
                    val validationResult = validateFields(editedTask)
                    if (validationResult is ValidationResult.Error) {
                        ToastManager.showShort(context, validationResult.messageId)
                        return@TaskDetailsAppBar
                    }

                    viewModel.addTask(editedTask)
                    navigateToTasksList()
                },
                onCloseClick = {
                    safeNavigateToTasksList()
                },
                onUpdateClick = { _ ->
                    val validationResult = validateFields(editedTask)
                    if (validationResult is ValidationResult.Error) {
                        ToastManager.showShort(context, validationResult.messageId)
                        return@TaskDetailsAppBar
                    }

                    if (editedTask != originalTask) {
                        viewModel.updateTask(editedTask)
                    }
                    navigateToTasksList()
                },
                onDeleteClick = { _ ->
                    viewModel.showDeleteConfirmation()
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

    if (deleteConfirmationVisibility) {
        ConfirmationDialog(
            title = stringResource(R.string.confirm_delete_title),
            message = stringResource(R.string.confirm_delete_message),
            onActionConfirmed = {
                viewModel.deleteTask(originalTask)
                navigateToTasksList()
            },
            onCloseRequest = {
                viewModel.hideDeleteConfirmation()
            },
        )
    }
    if (exitConfirmationVisibility) {
        ConfirmationDialog(
            title = stringResource(R.string.confirm_exit_title),
            message = stringResource(R.string.confirm_exit_message),
            onActionConfirmed = {
                navigateToTasksList()
            },
            onCloseRequest = {
                viewModel.hideExitConfirmation()
            },
        )
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

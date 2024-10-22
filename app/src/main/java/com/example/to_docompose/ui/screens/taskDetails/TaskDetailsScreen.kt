package com.example.to_docompose.ui.screens.taskDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.to_docompose.navigation.ToDoTaskAction

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

    Scaffold(
        topBar = {
            TaskDetailsAppBar(
                selectedTask = selectedTask,
                navigateToTasksList = navigateToTasksList,
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

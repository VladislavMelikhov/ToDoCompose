package com.example.to_docompose.ui.screens.tasksList

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.to_docompose.R
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.screens.tasksList.message.TaskMessage
import com.example.to_docompose.ui.screens.tasksList.message.toDisplayString
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette
import kotlinx.coroutines.launch

private const val TAG = "TasksListScreen"

@Composable
fun TasksListScreen(
    navigateToTaskDetails: (taskId: Int) -> Unit,
    viewModel: TasksListViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    val taskMessage: TaskMessage? by viewModel.taskMessage.collectAsState()
    taskMessage?.let { message ->
        scope.launch {
            Log.d(TAG, "showSnackbar, message: $message")
            when (message) {
                is TaskMessage.TaskAdded,
                is TaskMessage.TaskUpdated,
                is TaskMessage.TasksRestored -> {
                    snackbarHostState.showSnackbar(
                        message = message.toDisplayString(context),
                    )
                }
                is TaskMessage.TasksDeleted -> {
                    val result = snackbarHostState.showSnackbar(
                        message = message.toDisplayString(context),
                        actionLabel = context.getString(R.string.undo),
                        duration = SnackbarDuration.Long,
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.restoreTasks(message.tasks)
                    }
                }
            }
        }
        viewModel.onTaskMessageHandled()
    }

    val searchAppBarState: SearchAppBarState by viewModel.searchAppBarState.collectAsState()
    val searchQuery: String by viewModel.searchQuery.collectAsState()
    val tasks: List<ToDoTask> by viewModel.tasks.collectAsState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
        topBar = {
            TasksAppBar(
                tasks = tasks,
                searchAppBarState = searchAppBarState,
                searchQuery = searchQuery,
                onOpenSearchClick = viewModel::onOpenSearchClick,
                onCloseSearchClick = viewModel::onCloseSearchClick,
                onSearchQueryChange = viewModel::searchTasks,
                onDeleteAllClick = viewModel::deleteTasks
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                TasksListContent(
                    tasks = tasks,
                    onTaskClick = navigateToTaskDetails,
                )
            }
        },
        floatingActionButton = {
            TasksFab(
                onClick = { navigateToTaskDetails(-1) }
            )
        },
    )
}

@Composable
private fun TasksFab(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = LocalCustomColorsPalette.current.fabBackgroundColor,
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button),
            tint = Color.White,
        )
    }
}

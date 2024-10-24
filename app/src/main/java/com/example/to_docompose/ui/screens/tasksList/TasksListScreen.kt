package com.example.to_docompose.ui.screens.tasksList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.to_docompose.R
import com.example.to_docompose.ui.screens.tasksList.message.TaskMessage
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette

const val TASKS_LIST_ARG_KEY = "actionId"

@Composable
fun TasksListScreen(
    navigateToTaskDetails: (taskId: Int) -> Unit,
    viewModel: TasksListViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    val taskMessage: TaskMessage? by viewModel.taskMessage.collectAsState()

    LaunchedEffect(
        key1 = taskMessage,
        key2 = snackbarHostState,
    ) {
        taskMessage?.let { message ->
            snackbarHostState.showSnackbar(context.getString(message.messageId))
            viewModel.onTaskMessageHandled()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
        topBar = {
            TasksAppBar(
                viewModel = viewModel,
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                TasksListContent(
                    viewModel = viewModel,
                    navigateToTaskDetails = navigateToTaskDetails,
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

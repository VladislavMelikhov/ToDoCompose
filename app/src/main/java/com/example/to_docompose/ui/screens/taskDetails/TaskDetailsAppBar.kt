package com.example.to_docompose.ui.screens.taskDetails

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.navigation.ToDoTaskAction
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette
import com.example.to_docompose.ui.theme.ToDoComposeTheme

@Composable
fun TaskDetailsAppBar(
    navigateToTasksList: (ToDoTaskAction) -> Unit,
) {
    NewTaskAppBar(
        navigateToTasksList = navigateToTasksList,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(
    navigateToTasksList: (ToDoTaskAction) -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            BackAction(
                onClick = { navigateToTasksList(ToDoTaskAction.NO_ACTION) },
            )
        },
        title = {
            Text(
                text = stringResource(R.string.add_task),
                color = LocalCustomColorsPalette.current.topAppBarContentColor,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColorsPalette.current.topAppBarBackgroundColor,
        ),
        actions = {
            AddAction(
                onClick = { navigateToTasksList(ToDoTaskAction.ADD) },
            )
        },
    )
}

@Composable
fun BackAction(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_arrow),
            tint = LocalCustomColorsPalette.current.topAppBarContentColor,
        )
    }
}

@Composable
fun AddAction(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.add_task),
            tint = LocalCustomColorsPalette.current.topAppBarContentColor,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingTaskAppBar(
    task: ToDoTask,
    navigateToTasksList: (ToDoTaskAction) -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(
                onClick = { navigateToTasksList(ToDoTaskAction.NO_ACTION) },
            )
        },
        title = {
            Text(
                text = task.title,
                color = LocalCustomColorsPalette.current.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColorsPalette.current.topAppBarBackgroundColor,
        ),
        actions = {
            DeleteAction(
                onClick = { navigateToTasksList(ToDoTaskAction.DELETE) }
            )
            UpdateAction(
                onClick = { navigateToTasksList(ToDoTaskAction.UPDATE) }
            )
        },
    )
}

@Composable
fun CloseAction(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(R.string.close_icon),
            tint = LocalCustomColorsPalette.current.topAppBarContentColor,
        )
    }
}

@Composable
fun DeleteAction(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_icon),
            tint = LocalCustomColorsPalette.current.topAppBarContentColor,
        )
    }
}

@Composable
fun UpdateAction(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.update_icon),
            tint = LocalCustomColorsPalette.current.topAppBarContentColor,
        )
    }
}

@Composable
@Preview
fun NewTaskAppBarPreview() {
    ToDoComposeTheme(darkTheme = false) {
        NewTaskAppBar(
            navigateToTasksList = {},
        )
    }
}

@Composable
@Preview
fun ExistingTaskAppBarPreview() {
    ToDoComposeTheme(darkTheme = false) {
        ExistingTaskAppBar(
            task = ToDoTask(
                id = 123,
                title = "Long title of some task from the list",
                description = "Task description",
                priority = Priority.MEDIUM,
            ),
            navigateToTasksList = {},
        )
    }
}

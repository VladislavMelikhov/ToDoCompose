package com.example.to_docompose.ui.screens.taskDetails

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
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
                onBackClick = { navigateToTasksList(ToDoTaskAction.NO_ACTION) },
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
                onAddClick = { navigateToTasksList(ToDoTaskAction.ADD) },
            )
        },
    )
}

@Composable
fun BackAction(
    onBackClick: () -> Unit,
) {
    IconButton(
        onClick = onBackClick,
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
    onAddClick: () -> Unit,
) {
    IconButton(
        onClick = onAddClick,
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.add_task),
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

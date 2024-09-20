package com.example.to_docompose.ui.screens.tasksList

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.ui.theme.ComposeLocalWrapper
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette

@Composable
fun TasksListScreen(
    navigateToTaskDetails: (taskId: Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TasksAppBar()
        },
        content = { padding ->
            Text(
                text = "Tasks List",
                modifier = Modifier
                    .padding(padding),
            )
        },
        floatingActionButton = {
            TasksFab(
                onClick = { navigateToTaskDetails(-1) }
            )
        },
    )
}

@Composable
fun TasksFab(
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

@Composable
@Preview
fun TasksListPreview() {
    ComposeLocalWrapper {
        TasksListScreen(
            navigateToTaskDetails = {},
        )
    }
}

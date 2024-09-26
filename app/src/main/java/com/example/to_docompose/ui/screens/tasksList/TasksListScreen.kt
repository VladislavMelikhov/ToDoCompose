package com.example.to_docompose.ui.screens.tasksList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.to_docompose.R
import com.example.to_docompose.ui.shared.SharedViewModel
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette

@Composable
fun TasksListScreen(
    navigateToTaskDetails: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    Scaffold(
        topBar = {
            TasksAppBar(
                sharedViewModel = sharedViewModel,
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                TasksListContent(
                    sharedViewModel = sharedViewModel,
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

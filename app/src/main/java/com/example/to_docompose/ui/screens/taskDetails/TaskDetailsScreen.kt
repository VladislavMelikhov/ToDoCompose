package com.example.to_docompose.ui.screens.taskDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.to_docompose.navigation.ToDoTaskAction

@Composable
fun TaskDetailsScreen(
    navigateToTasksList: (ToDoTaskAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TaskDetailsAppBar(
                navigateToTasksList = navigateToTasksList,
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {

            }
        },
    )
}

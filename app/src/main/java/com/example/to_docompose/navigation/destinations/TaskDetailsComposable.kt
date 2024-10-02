package com.example.to_docompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.navigation.ToDoTaskAction
import com.example.to_docompose.ui.screens.taskDetails.TaskDetailsScreen

fun NavGraphBuilder.taskDetailsComposable(
    navigateToTasksList: (ToDoTaskAction) -> Unit,
) {
    composable(
        route = "task/{taskId}",
        arguments = listOf(
            navArgument("taskId") {
                type = NavType.IntType
            }
        )
    ) {
        TaskDetailsScreen(
            navigateToTasksList = navigateToTasksList,
        )
    }
}

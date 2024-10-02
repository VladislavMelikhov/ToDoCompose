package com.example.to_docompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.navigation.ToDoTaskAction
import com.example.to_docompose.ui.screens.tasksList.TasksListScreen

fun NavGraphBuilder.tasksListComposable(
    navigateToTaskDetails: (taskId: Int) -> Unit,
) {
    composable(
        route = "list/{actionId}",
        arguments = listOf(
            navArgument("actionId") {
                type = NavType.IntType
                defaultValue = ToDoTaskAction.NO_ACTION.id
            }
        )
    ) {
        TasksListScreen(
            navigateToTaskDetails = navigateToTaskDetails,
        )
    }
}

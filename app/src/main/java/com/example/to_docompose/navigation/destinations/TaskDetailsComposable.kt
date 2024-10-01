package com.example.to_docompose.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.navigation.ToDoTaskAction
import com.example.to_docompose.ui.screens.taskDetails.TaskDetailsScreen
import com.example.to_docompose.ui.shared.SharedViewModel

private const val TAG = "TaskDetailsComposable"

fun NavGraphBuilder.taskDetailsComposable(
    sharedViewModel: SharedViewModel,
    navigateToTasksList: (ToDoTaskAction) -> Unit,
) {
    composable(
        route = "task/{taskId}",
        arguments = listOf(
            navArgument("taskId") {
                type = NavType.IntType
            }
        )
    ) { navBackStackEntry ->
        val args = navBackStackEntry.arguments!!
        val taskId = args.getInt("taskId")

        LaunchedEffect(taskId) {
            sharedViewModel.selectTask(taskId)
        }

        val selectedTask by sharedViewModel.selectedTask.collectAsState()
        TaskDetailsScreen(
            selectedTask = selectedTask,
            navigateToTasksList = navigateToTasksList,
        )
    }
}

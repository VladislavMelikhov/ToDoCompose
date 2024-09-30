package com.example.to_docompose.navigation.destinations

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.navigation.ToDoTaskAction
import com.example.to_docompose.ui.screens.taskDetails.TaskDetailsScreen

private const val TAG = "TaskDetailsComposable"

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
    ) { navBackStackEntry ->
        val args = navBackStackEntry.arguments!!

        val taskId = args.getInt("taskId")
        Log.d(TAG, "taskId = $taskId")

        TaskDetailsScreen(
            navigateToTasksList = navigateToTasksList,
        )
    }
}

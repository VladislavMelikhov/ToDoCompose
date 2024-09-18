package com.example.to_docompose.navigation.destinations

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.navigation.ToDoTaskAction

fun NavGraphBuilder.tasksListComposable(
    navigateToTaskDetails: (Int) -> Unit,
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
        val actionId = it.arguments?.getInt("actionId")
        Text("tasksList, actionId: $actionId")
    }
}

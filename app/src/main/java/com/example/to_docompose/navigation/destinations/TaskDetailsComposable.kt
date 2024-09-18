package com.example.to_docompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.tasksDetailsComposable(
    navigateToTasksList: (Int) -> Unit,
) {
    composable(
        route = "task/{taskId}",
        arguments = listOf(
            navArgument("taskId") {
                type = NavType.IntType
            }
        )
    ) {

    }
}

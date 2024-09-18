package com.example.to_docompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.to_docompose.navigation.destinations.tasksDetailsComposable
import com.example.to_docompose.navigation.destinations.tasksListComposable

@Composable
fun SetupNavigation(
    navController: NavHostController
) {
    val screens = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = "list/{actionId}",
    ) {
        tasksListComposable(
            navigateToTaskDetails = screens.task
        )
        tasksDetailsComposable(
            navigateToTasksList = screens.list
        )
    }
}

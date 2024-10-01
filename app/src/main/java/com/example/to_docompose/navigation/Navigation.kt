package com.example.to_docompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.to_docompose.navigation.destinations.taskDetailsComposable
import com.example.to_docompose.navigation.destinations.tasksListComposable
import com.example.to_docompose.ui.shared.SharedViewModel

@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
) {
    val screens = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = "list/{actionId}",
    ) {
        tasksListComposable(
            navigateToTaskDetails = screens.task,
            sharedViewModel = sharedViewModel,
        )
        taskDetailsComposable(
            navigateToTasksList = screens.list,
            sharedViewModel = sharedViewModel,
        )
    }
}

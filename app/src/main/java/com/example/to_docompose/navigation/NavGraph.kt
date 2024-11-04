package com.example.to_docompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.ui.screens.splash.SplashScreen
import com.example.to_docompose.ui.screens.taskDetails.TASK_DETAILS_ARG_KEY
import com.example.to_docompose.ui.screens.taskDetails.TaskDetailsScreen
import com.example.to_docompose.ui.screens.tasksList.TasksListScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(
            route = Screen.Splash.route,
        ) {
            SplashScreen(
                navigateToTasksList = {
                    navController.navigate(Screen.TasksList.routeToNavigate()) {
                        popUpTo(Screen.Splash.routeToNavigate()) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable(
            route = Screen.TasksList.route,
        ) {
            TasksListScreen(
                navigateToTaskDetails = { taskId ->
                    navController.navigate(Screen.TaskDetails.routeToNavigate(taskId))
                },
            )
        }
        composable(
            route = Screen.TaskDetails.route,
            arguments = listOf(navArgument(TASK_DETAILS_ARG_KEY) { type = NavType.IntType })
        ) {
            TaskDetailsScreen(
                navigateToTasksList = {
                    navController.popBackStack(Screen.TasksList.routeToNavigate(), inclusive = false)
                },
            )
        }
    }
}

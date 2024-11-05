package com.example.to_docompose.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.tween
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

private const val ANIMATION_DURATION = 300

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
            exitTransition = {
                slideOutOfContainer(SlideDirection.Up)
            }
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
            arguments = listOf(navArgument(TASK_DETAILS_ARG_KEY) { type = NavType.IntType }),
            enterTransition = {
                slideIntoContainer(SlideDirection.Start, tween(ANIMATION_DURATION))
            }
        ) {
            TaskDetailsScreen(
                navigateToTasksList = {
                    navController.popBackStack(Screen.TasksList.routeToNavigate(), inclusive = false)
                },
            )
        }
    }
}

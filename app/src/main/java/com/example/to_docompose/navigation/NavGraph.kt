package com.example.to_docompose.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.to_docompose.ui.screens.splash.SplashScreen
import com.example.to_docompose.ui.screens.taskDetails.TaskDetailsScreen
import com.example.to_docompose.ui.screens.tasksList.TasksListScreen

private const val ANIMATION_DURATION = 300

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash,
    ) {
        composable<Screen.Splash>(
            exitTransition = {
                slideOutOfContainer(SlideDirection.Up)
            }
        ) {
            SplashScreen(
                navigateToTasksList = {
                    navController.navigate(Screen.TasksList) {
                        popUpTo(Screen.Splash) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable<Screen.TasksList> {
            TasksListScreen(
                navigateToTaskDetails = { taskId ->
                    navController.navigate(Screen.TaskDetails(taskId))
                },
            )
        }
        composable<Screen.TaskDetails>(
            enterTransition = {
                slideIntoContainer(SlideDirection.Start, tween(ANIMATION_DURATION))
            }
        ) {
            TaskDetailsScreen(
                navigateToTasksList = {
                    navController.popBackStack(Screen.TasksList, inclusive = false)
                },
            )
        }
    }
}

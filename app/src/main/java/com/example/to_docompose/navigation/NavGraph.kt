package com.example.to_docompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.ui.screens.taskDetails.TASK_DETAILS_ARG_KEY
import com.example.to_docompose.ui.screens.taskDetails.TaskDetailsScreen
import com.example.to_docompose.ui.screens.tasksList.TASKS_LIST_ARG_KEY
import com.example.to_docompose.ui.screens.tasksList.TasksListScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.TasksList.route,
    ) {
        composable(
            route = Screen.TasksList.route,
            arguments = listOf(navArgument(TASKS_LIST_ARG_KEY) {
                type = NavType.IntType
                defaultValue = ToDoTaskAction.NO_ACTION.id
            })
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
                navigateToTasksList = { action ->
                    navController.navigate(Screen.TasksList.routeToNavigate(action.id)) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                },
            )
        }
    }
}
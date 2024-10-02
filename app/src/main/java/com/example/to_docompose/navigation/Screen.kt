package com.example.to_docompose.navigation

import com.example.to_docompose.ui.screens.taskDetails.TASK_DETAILS_ARG_KEY
import com.example.to_docompose.ui.screens.tasksList.TASKS_LIST_ARG_KEY

sealed class Screen(val route: String) {

    data object TasksList : Screen("tasks_list/{$TASKS_LIST_ARG_KEY}") {
        fun routeToNavigate(actionId: Int) = "tasks_list/$actionId"
    }

    data object TaskDetails : Screen("task_details/{$TASK_DETAILS_ARG_KEY}") {
        fun routeToNavigate(taskId: Int) = "task_details/$taskId"
    }
}
